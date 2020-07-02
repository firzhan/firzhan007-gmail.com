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
class apim_analytics_dashboard::params {


  # Define the template
  $start_script_template = "bin/dashboard.sh"

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

  $profile = "apim_analytics_dashboard"
  $target = "/mnt"
  $product_dir = "${target}/${profile}"
  $pack_dir = "${target}/${profile}/packs"
  $wso2_service_name = "wso2${profile}"

  # ----- Profile configs -----
  $pack = "wso2am-analytics-${version}"
  $server_script_path = "${product_dir}/${pack}/bin/dashboard.sh"
  $pid_file_path = "${product_dir}/${pack}/wso2/dashboard/runtime.pid"

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

  $worker_node_admin_username = 'admin'
  $worker_node_admin_password = 'admin'

  # Configuration used for the databridge communication

  # Configuration of the Data Agents - to publish events through

  # Secure Vault Configuration

  # Data Sources Configurations

  $dashboard_db_url  = 'jdbc:h2:${sys:carbon.home}/wso2/${sys:wso2.runtime}/database/DASHBOARD_DB_1;IFEXISTS=TRUE;DB_CLOSE_ON_EXIT=FALSE;LOCK_TIMEOUT=60000;MVCC=TRUE'
  $dashboard_db_username = 'wso2carbon'
  $dashboard_db_password = 'wso2carbon'
  $dashboard_db_driver = 'org.h2.Driver'
  $dashboard_db_test_query = 'SELECT 1'

  #$dashboard_db_url  = 'jdbc:oracle:thin:@<RDS_DNS>:1521/WSO2_DASHBOARD_DB'
  #$dashboard_db_username = 'wso2carbon'
  #$dashboard_db_password = 'wso2carbon'
  #$dashboard_db_driver = 'oracle.jdbc.driver.OracleDriver'
  #$dashboard_db_test_query = 'SELECT 1 FROM DUAL'

  $permission_db_url  = 'jdbc:h2:${sys:carbon.home}/wso2/${sys:wso2.runtime}/database/PERMISSION_DB_1;IFEXISTS=TRUE;DB_CLOSE_ON_EXIT=FALSE;LOCK_TIMEOUT=60000;MVCC=TRUE'
  $permission_db_username = 'wso2carbon'
  $permission_db_password = 'wso2carbon'
  $permission_db_driver = 'org.h2.Driver'
  $permission_db_test_query = 'SELECT 1'

  #$permission_db_url  = 'jdbc:oracle:thin:@<RDS_DNS>:1521/PERMISSION_DB'
  #$permission_db_username = 'wso2carbon'
  #$permission_db_password = 'wso2carbon'
  #$permission_db_driver = 'oracle.jdbc.driver.OracleDriver'
  #$permission_db_test_query = 'SELECT 1 FROM DUAL'

  $apim_analytics_db_url  = 'jdbc:h2:${sys:carbon.home}/wso2/${sys:wso2.runtime}/database/APIM_ANALYTICS_DB_1;IFEXISTS=TRUE;DB_CLOSE_ON_EXIT=FALSE;LOCK_TIMEOUT=60000;MVCC=TRUE'
  $apim_analytics_db_username = 'wso2carbon'
  $apim_analytics_db_password = 'wso2carbon'
  $apim_analytics_db_driver = 'org.h2.Driver'
  $apim_analytics_db_test_query = 'SELECT 1'


  #$apim_analytics_db_url  = 'jdbc:oracle:thin:@<RDS_DNS>:1521/APIM_ANALYTICS_DB'
  #$apim_analytics_db_username = 'wso2carbon'
  #$apim_analytics_db_password = 'wso2carbon'
  #$apim_analytics_db_driver = 'oracle.jdbc.driver.OracleDriver'
  #$apim_analytics_db_test_query = 'SELECT 1 FROM DUAL'


  $am_db_url  = 'jdbc:h2:${sys:carbon.home}/wso2/${sys:wso2.runtime}/database/AM_DB_1;IFEXISTS=TRUE;DB_CLOSE_ON_EXIT=FALSE;LOCK_TIMEOUT=60000;MVCC=TRUE'
  $am_db_username = 'wso2carbon'
  $am_db_password = 'wso2carbon'
  $am_db_driver = 'org.h2.Driver'
  $am_db_test_query = 'SELECT 1'

  #$am_db_url  = 'jdbc:oracle:thin:@<RDS_DNS>:1521/AM_DB'
  #$am_db_username = 'wso2carbon'
  #$am_db_password = 'wso2carbon'
  #$am_db_driver = 'oracle.jdbc.driver.OracleDriver'
  #$am_db_test_query = 'SELECT 1 FROM DUAL'

  $business_rules_db_url  = 'jdbc:h2:${sys:carbon.home}/wso2/${sys:wso2.runtime}/database/BUSINESS_RULES_DB_1;DB_CLOSE_ON_EXIT=FALSE;LOCK_TIMEOUT=60000;MVCC=TRUE'
  $business_rules_db_username = 'wso2carbon'
  $business_rules_db_password = 'wso2carbon'
  $business_rules_db_driver = 'org.h2.Driver'
  $business_rules_db_test_query = 'SELECT 1'

  #$business_rules_db_url  = 'jdbc:oracle:thin:@<RDS_DNS>:1521/WSO2_BUSINESS_RULES_DB'
  #$business_rules_db_username = 'wso2carbon'
  #$business_rules_db_password = 'wso2carbon'
  #$business_rules_db_driver = 'oracle.jdbc.driver.OracleDriver'
  #$business_rules_db_test_query = 'SELECT 1 FROM DUAL'

  $admin_username = 'admin'
  $admin_password = 'admin'

  # DNS Portal DNS entry
  $dev_portal_dns_name = "localhost"
  $dev_portal_dns_port = 9443

  # Define the template
  $template_list = [
    'conf/dashboard/deployment.yaml'
  ]

  # Define file list
  $file_list = [
    'lib/ojdbc8_1.0.0.jar'
  ]

  # -------------- Deployment.yaml Config -------------- #
  # Carbon Configuration Parameters
  $wso2_carbon_id = 'wso2-am-analytics-dashboard'
  $dashboard_dns_name = 'localhost'

  # worker configurations
  # ip: ip of the worker node
  # port: wso2_transport_msf4j_https_port of the worker node given under host_vars
  $worker_nodes = [
    {
      ip => 'localhost',
      port => '9444'
    }
  ]

  $worker_node_dns_admin_username = 'admin'
  $worker_node_dns_admin_password = 'admin'
}
