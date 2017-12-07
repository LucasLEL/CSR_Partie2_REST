package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.inria.restlet.mta.backend.Gare;
import org.inria.restlet.mta.internals.Train;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class TrainsResource extends ServerResource {
	
	 /** Backend. */
    private Gare backend_;
    
    //private User user_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public TrainsResource()
    {
        super();
        backend_ = (Gare) getApplication().getContext().getAttributes()
            .get("backend");
    }
    
    @Get("json")
    public Representation getTrains() throws JSONException
    {
        Collection<Train> trains = backend_.getListTrains();
        Collection<JSONObject> jsonTrains = new ArrayList<JSONObject>();  

        for (Train train : trains)
        {
            JSONObject current = new JSONObject();
            current.put("idTrain", train.getIdTrain());
            current.put("capacite_train", train.getCAPACITE_TRAIN());
            current.put("places_libres", train.getNbPlacesLibre());
            current.put("etat_train", train.getEtatTrain());
            jsonTrains.add(current);

        }
        JSONArray jsonArray = new JSONArray(jsonTrains);
        return new JsonRepresentation(jsonArray);
    }
    
    @Post("json")
    public Representation createTrain(JsonRepresentation representation)
        throws Exception
    {
        JSONObject object = representation.getJsonObject();
        int id_train = object.getInt("id");
        int capacite_train = object.getInt("capacite");

        // Save the tweet
        Train train = backend_.ajoutTrain(id_train, capacite_train);

        // generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("id", train.getIdTrain());
        resultObject.put("capacite", train.getCAPACITE_TRAIN());
        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;
    }
    
    

}
