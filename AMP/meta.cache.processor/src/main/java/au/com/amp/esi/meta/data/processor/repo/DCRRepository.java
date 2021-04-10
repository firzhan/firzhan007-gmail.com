package com.example.spring.demo.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.spring.demo.entity.DCREvent;
import com.example.spring.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DCRRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public DCREvent addDCREvent(DCREvent dcrEvent){
        dynamoDBMapper.save(dcrEvent);
        return dcrEvent;
    }

    public DCREvent findPersonBySoftwareID(String softwareId){
        return dynamoDBMapper.load(DCREvent.class, softwareId);
    }

    public void deletePerson(DCREvent dcrEvent){
        dynamoDBMapper.delete(dcrEvent);
    }

    public DCREvent update(DCREvent dcrEvent){
        dynamoDBMapper.save(dcrEvent, buildExpression(dcrEvent));
        return dcrEvent;
    }

    private DynamoDBSaveExpression buildExpression(DCREvent dcrEvent){

        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("softwareId",
                new ExpectedAttributeValue(new AttributeValue().withS(dcrEvent.getSoftwareId())));

        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
        return dynamoDBSaveExpression;
    }



}
