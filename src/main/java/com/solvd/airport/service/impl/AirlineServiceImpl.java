package com.solvd.airport.service.impl;

import com.solvd.airport.bin.Airlines;
import com.solvd.airport.dao.AirlinesDao;
import com.solvd.airport.dao.impl.AirlinesDAOImpl;
import com.solvd.airport.handlers.AirlinesHandler;
import com.solvd.airport.service.AirlineService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AirlineServiceImpl implements AirlineService {
    private static final Logger logger = LogManager.getLogger(AirlineServiceImpl.class);
    AirlinesDao airlinesDao = new AirlinesDAOImpl();

    @Override
    public void insertAirline(Airlines airline) {
        if (airline != null) {
            airlinesDao.insert(airline);
        } else {
            throw new NullPointerException("airline object is null. Cannot insert.");
        }
    }

    @Override
    public void updateAirline(Airlines airline) {
        airlinesDao.update(airline);
    }

    @Override
    public void deleteAirline(Airlines airline) {
        airlinesDao.delete(airline);
    }

    @Override
    public Airlines getResult(String uri) {
        AirlinesHandler airlinesHandler = new AirlinesHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(uri), airlinesHandler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(e.getMessage());
        }
        Airlines airlines =  airlinesHandler.getResult();
        return airlines;
    }

}