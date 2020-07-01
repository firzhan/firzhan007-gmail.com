# ----------------------------------------------------------------------------
#  Copyright (c) 2018 WSO2, Inc. http://www.wso2.org
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
# ----------------------------------------------------------------------------

# Class: apim_analytics_worker
# Init class of API Manager Analytics - Worker profile
class apim_analytics_dashboard inherits apim_analytics_dashboard::params {

  include '::apim_analytics_dashboard::service'


  # Install system packages
  package { $packages:
    ensure => installed
  }


  # Create wso2 group
  group { $user_group:
    ensure => present,
    gid    => $user_group_id,
    system => true,
  }

  # Create wso2 user
  user { $user:
    ensure  => present,
    uid     => $user_id,
    gid     => $user_group_id,
    home    => "/home/${user}",
    system  => true,
    require => Group["${user_group}"]
  }

  /*
  * System Performance Tuning
  */
  if $enable_performance_tuning {
    $performance_tuning_flie_list.each | String $file | {
      file { "/${file}":
        path    => "/${file}",
        ensure  => present,
        recurse => remote,
        source  => "puppet:///modules/${module_name}/system/${file}",
        owner   => root,
        group   => root
      }
    }
  }

  # Create symlink to Java binary
  file { "${java_symlink}":
    ensure  => "link",
    target  => "${java_home}"
  }

  /*
 * WSO2 Distribution
 */

  file { ["${product_dir}", "${pack_dir}"]:
    ensure  => directory,
    owner   => $user,
    group   => $user_group,
    require => [ User["${user}"], Group["${user_group}"] ]
  }

  # Copy binary to distribution path
  file { "unzip-pack":
    path    => "${pack_dir}/${product_binary}",
    ensure  => present,
    notify  => [Exec["stop-server"], Exec["detele-pack"], Exec["unzip-update"]],
  }

  # Stop the existing setup
  exec { "stop-server":
    command   => "systemctl stop ${wso2_service_name}",
    path      => [ '/bin/', '/sbin/', '/usr/bin/', '/usr/sbin/' ],
    tries     => $try_count,
    try_sleep => $try_sleep,
    onlyif    => "/usr/bin/test -f /etc/systemd/system/${wso2_service_name}.service",
  }

  # Delete existing setup
  exec { "detele-pack":
    command     => "rm -rf ${carbon_home}",
    path        => "/bin/",
    onlyif      => "/usr/bin/test -d ${carbon_home}",
    subscribe   => Exec["stop-server"],
    refreshonly => true,
  }

  # Unzip the binary and create setup
  exec { "unzip-update":
    command => "unzip -o ${product_binary} -d ${product_dir}",
    path    => "/usr/bin/",
    user    => $user,
    group   => $user_group,
    cwd     => "${pack_dir}",
  }

  # Copy the unit file required to deploy the server as a service
  file { "/etc/systemd/system/${wso2_service_name}.service":
    ensure  => present,
    owner   => root,
    group   => root,
    mode    => '0754',
    content => template("${module_name}/carbon.service.erb"),
  }

  exec { 'systemctl daemon-reload':
    path        => '/bin/:/sbin/:/usr/bin/:/usr/sbin/',
    subscribe   => File["/etc/systemd/system/${wso2_service_name}.service"],
    refreshonly => true,
  }


  # Copy configuration changes to the installed directory
  $template_list.each |String $template| {
    file { "${carbon_home}/${template}":
      ensure  => file,
      mode    => '0644',
      content => template("${module_name}/carbon-home/${template}.erb"),
      notify  => Service["${wso2_service_name}"]
      #require => Class["apim_common"]
    }
  }

  # Copy files to carbon home directory
  $file_list.each | String $file | {
    file { "${carbon_home}/${file}":
      ensure => present,
      owner => $user,
      recurse => remote,
      group => $user_group,
      mode => '0755',
      source => "puppet:///modules/${module_name}/${file}",
      notify  => Service["${wso2_service_name}"]
    }
  }


  # Copy wso2server.sh to installed directory
  file { "${carbon_home}/${start_script_template}":
    ensure  => file,
    owner   => $user,
    group   => $user_group,
    mode    => '0754',
    content => template("${module_name}/carbon-home/${start_script_template}.erb"),
    notify  => Service["${wso2_service_name}"]
  }

  /*
    Following script can be used to copy file to a given location.
    This will copy some_file to install_path -> repository.
    Note: Ensure that file is available in modules -> apim_analytics_worker -> files
  */
  # file { "${install_path}/repository/some_file":
  #   owner  => $user,
  #   group  => $user_group,
  #   mode   => '0644',
  #   source => "puppet:///modules/${module_name}/some_file",
  # }
}
