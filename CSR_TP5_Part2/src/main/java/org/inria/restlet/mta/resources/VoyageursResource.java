package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;

import org.inria.restlet.mta.backend.Gare;
import org.inria.restlet.mta.internals.Voyageur;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 * Resource exposing the users
 *
 */
public class VoyageursResource extends ServerResource
{

    /** Backend. */
    private Gare backend_;
    

    /**
     * Constructor.
     * Call for every single user request.
     */
    public VoyageursResource()
    {
        super();
        backend_ = (Gare) getApplication().getContext().getAttributes()
            .get("backend");
    }

    /**
     *
     * Returns the list of all the users
     *
     * @return  JSON representation of the users
     * @throws JSONException
     */
    @Get("json")
    public Representation getVoyageurs() throws JSONException
    {
        Collection<Voyageur> voyageurs = backend_.getListVoyageurs();
        Collection<JSONObject> jsonVoyageurs = new ArrayList<JSONObject>();

        for (Voyageur voyageur : voyageurs)
        {
            JSONObject current = new JSONObject();
            current.put("idVoyageur", voyageur.getIdVoyageur());
            current.put("etat_voyageur", voyageur.getEtatVoyageur());
            jsonVoyageurs.add(current);

        }
        JSONArray jsonArray = new JSONArray(jsonVoyageurs);
        return new JsonRepresentation(jsonArray);
    }

    @Post("json")
    public Representation createVoyageur(JsonRepresentation representation)
        throws Exception
    {
        JSONObject object = representation.getJsonObject();
        int id = object.getInt("id");

        // Save the user
        Voyageur voyageur = backend_.ajoutVoyageur(id);

        // generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("id", voyageur.getId());
        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;
    }

}
