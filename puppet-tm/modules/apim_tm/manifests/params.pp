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

# Claas apim_tm::params
# This class includes all the necessary parameters.
class apim_tm::params {

  $start_script_template = 'bin/wso2server.sh'
  $jvmxms = '256m'
  $jvmxmx = '1024m'

  $template_list = [
    'repository/conf/deployment.toml',
    'repository/conf/datasources/persistence-datasources.xml',
    'repository/resources/conf/templates/repository/conf/event-processor.xml.j2',

  ]

  # Define file list
  $file_list = [
    'repository/components/lib/ojdbc8.jar',

  ]

  $packages = ["unzip"]
  $version = "3.1.0"

  # Set the location the product packages should reside in (eg: "local" in the /files directory, "remote" in a remote location)
  $pack_location = "local"
  # $pack_location = "remote"
  # $remote_jdk = "<URL_TO_JDK_FILE>"

  $user = 'wso2carbon'
  $user_group = 'wso2'
  $user_id = 802
  $user_group_id = 802

  # Performance tuning configurations
  $enable_performance_tuning = false
  $performance_tuning_flie_list = [
    'etc/sysctl.conf',
    'etc/security/limits.conf',
  ]

  # JDK Distributions
  $java_dir = "/opt"
  $java_symlink = "${java_dir}/java"
  $jdk_name = 'jdk1.8.0_241'
  $java_home = "${java_dir}/${jdk_name}"

  $profile = "apim_tm"
  $target = "/mnt"
  $product_dir = "${target}/${profile}"
  $pack_dir = "${target}/${profile}/packs"
  $wso2_service_name = "wso2${profile}"
  $pack = "wso2am-${version}"
  # $remote_pack = "<URL_TO_APIM_TRAFFICMANAGER_PACK>"
  $server_script_path = "${product_dir}/${pack}/bin/wso2server.sh"
  $pid_file_path = "${product_dir}/${pack}/wso2carbon.pid"
  $optimize_params = "-Dprofile=traffic-manager"

  # Pack Directories
  $carbon_home = "${product_dir}/${pack}"
  $product_binary = "${pack}.zip"

  #carbon_home = /mnt/apim_tm/wso2am-3.0.0
  #product_dir = /mnt/apim_tm


  # Server stop retry configs
  $try_count = 5
  $try_sleep = 5








  # ----- Carbon.xml config params -----
  /*
     Host name or IP address of the machine hosting this server
     e.g. www.wso2.org, 192.168.1.10
     This is will become part of the End Point Reference of the
     services deployed on this server instance.
  */



  # ----- Master-datasources config params -----

  $hostname = 'localhost'
  $node_ip = '127.0.0.1'

  $wso2shared_db_url = 'jdbc:h2:./repository/database/WSO2SHARED_DB;DB_CLOSE_ON_EXIT=FALSE'
  $wso2shared_db_username = 'wso2carbon'
  $wso2shared_db_password = 'wso2carbon'
  $wso2shared_db_driver = 'org.h2.Driver'
  $wso2shared_db_type = 'h2'
  $wso2shared_db_validation_query = 'SELECT 1'

  #$wso2shared_db_url = 'jdbc:oracle:thin:@<RDS_DNS>:1521/WSO2_SHARED_DB'
  #$wso2shared_db_username = '<WSO2_SHARED_DB>'
  #$wso2shared_db_password = '<PASSWORD>'
  #$wso2shared_db_type = 'oracle'
  #$wso2shared_db_driver = 'oracle.jdbc.driver.OracleDriver'
  #$wso2shared_db_validation_query = 'SELECT 1 FROM DUAL'


  # ----- user-mgt.xml config params -----
  $admin_username = 'admin'
  $admin_password = 'admin'

  # ----- event-processor.xml config params -----
  $event_processor_enable_single_mode = true
  $event_processor_enable_persistence = true

  # ----- persistence-datastore.xml config params
  $wso2persistence_db_url = "jdbc:oracle:thin:@localhost:1521/PERSISTENCE_DB_1"
  $wso2persistence_db_username = 'PERSISTENCE_DB_1'
  $wso2persistence_db_password = 'PERSISTENCE_DB_1_PASSWORD'
  $wso2persistence_db_driver = 'oracle.jdbc.driver.OracleDriver'


}
