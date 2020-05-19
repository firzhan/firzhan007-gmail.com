# puppet-apim
puppet-apim

This is a simplified puppet module for the API Manager 3.1.0

Parent repo could be found at the WSO2's repo [https://github.com/wso2/puppet-apim]

Following commands have to be executed at the Agent node to perform the puppet apply

export FACTER_environment=dev
export FACTER_profile=apim_tm
puppet agent -vt
