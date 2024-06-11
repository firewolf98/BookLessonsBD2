package model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

public class DBConnector {
	private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "booklessons";

    public static MongoDatabase getDatabase() {
        try {
            MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);

            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            System.out.println("Connessione al database 'booklessons' riuscita.");

            return database;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante la connessione al database", e);
        }
    }
    
    public static GridFSBucket getGridFSBucket() {
        return GridFSBuckets.create(getDatabase());
    }
}
