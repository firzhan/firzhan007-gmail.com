package com.example.spring.demo.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "dcr_events")
public class DCREvent implements Serializable {

    @DynamoDBHashKey(attributeName = "software_id")
    private String softwareId;

    @DynamoDBAttribute(attributeName = "org_id")
    private String orgId;

    @DynamoDBAttribute(attributeName = "adr_client_id")
    private String adrClientId;

    @DynamoDBAttribute(attributeName = "adr_client_name")
    private String adrClientName;

    @DynamoDBAttribute(attributeName = "last_status")
    private String lastStatus;
}
