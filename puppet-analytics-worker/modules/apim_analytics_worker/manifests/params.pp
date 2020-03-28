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

# Claas apim_analytics_worker::params
# This class includes all the necessary parameters.
class apim_analytics_worker::params {


  $packages = ["unzip"]
  $version = "3.0.0"

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

  $profile = "apim_analytics_worker"
  $target = "/mnt"
  $product_dir = "${target}/${profile}"
  $pack_dir = "${target}/${profile}/packs"
  $wso2_service_name = "wso2${profile}"

  # ----- Profile configs -----
  case $profile {
    'apim_tm': {
      $pack = "wso2am-${version}"
      # $remote_pack = "<URL_TO_APIM_TRAFFICMANAGER_PACK>"
      $server_script_path = "${product_dir}/${pack}/bin/wso2server.sh"
      $pid_file_path = "${product_dir}/${pack}/wso2carbon.pid"
      $optimize_params = "-Dprofile=traffic-manager"
    }
    'apim_analytics_worker': {
      $pack = "wso2am-analytics-${version}"
      # $remote_pack = "<URL_TO_APIM_ANALYTICS_WORKER_PACK>"
      $server_script_path = "${product_dir}/${pack}/bin/worker.sh"
      $pid_file_path = "${product_dir}/${pack}/wso2/worker/runtime.pid"
    }
    default: {
      $pack = "wso2am-${version}"
      # $remote_pack = "<URL_TO_APIM_PACK>"
      $server_script_path = "${product_dir}/${pack}/bin/wso2server.sh"
      $pid_file_path = "${product_dir}/${pack}/wso2carbon.pid"
      $optimize_params = ""
    }
  }

  # Pack Directories
  $carbon_home = "${product_dir}/${pack}"
  $product_binary = "${pack}.zip"

  # Server stop retry configs
  $try_count = 5
  $try_sleep = 5



  # ----- Master-datasources config params -----

  #$apim_analytics_db_url = 'jdbc:oracle:thin:@<RDS_DNS>:1521/AM_STATS_DB'
  #$apim_analytics_db_username = '<AM_STAT_DB>'
  #$apim_analytics_db_password = 'PASSWORD'
  #$apim_analytics_db_driver = 'oracle.jdbc.driver.OracleDriver'
  #$apim_analytics_db_test_query = 'SELECT 1 FROM DUAL'

  # ----- Carbon.xml config params -----


  # ----- user-mgt.xml config params -----

  # ----- Analytics config params -----

  # Configuration used for the databridge communication

  # Configuration of the Data Agents - to publish events through

  # Secure Vault Configuration

  # Data Sources Configurations


  $apim_analytics_db_url = 'jdbc:h2:${sys:carbon.home}/wso2/worker/database/WSO2AM_STATS_DB;AUTO_SERVER=TRUE'
  $apim_analytics_db_username = 'wso2carbon'
  $apim_analytics_db_password = 'wso2carbon'
  $apim_analytics_db_driver = 'org.h2.Driver'
  $apim_analytics_db_test_query = 'SELECT 1'

  # Define the template
  $start_script_template = "bin/worker.sh"

  # Define the template
  $template_list = [
    'conf/worker/deployment.yaml'
  ]

  # Define file list
  $file_list = [
    'lib/ojdbc8_1.0.0.jar'
  ]

  # -------------- Deployment.yaml Config -------------- #
  $node_shard_id = 'wso2-sp-analytics-1'
  # Carbon Configuration Parameters
  $wso2_carbon_id = 'wso2-am-analytics'

  $hostname = 'localhost'
}
