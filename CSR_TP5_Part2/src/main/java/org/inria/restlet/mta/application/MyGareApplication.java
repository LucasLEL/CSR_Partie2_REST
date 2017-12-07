package org.inria.restlet.mta.application;


import org.inria.restlet.mta.resources.VoyageursResource;
import org.inria.restlet.mta.resources.TrainsResource;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 *
 * Application.
 *
 * @author msimonin
 *
 */
public class MyGareApplication extends Application
{

    public MyGareApplication(Context context)
    {
        super(context);
    }

    @Override
    public Restlet createInboundRoot()
    {
        Router router = new Router(getContext());
        router.attach("/voyageurs", VoyageursResource.class);
        router.attach("/trains", TrainsResource.class);
 

        return router;
    }
}
