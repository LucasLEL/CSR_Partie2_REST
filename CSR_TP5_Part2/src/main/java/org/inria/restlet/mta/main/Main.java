package org.inria.restlet.mta.main;

import org.inria.restlet.mta.application.MyGareApplication;
import org.inria.restlet.mta.backend.Gare;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;

/**
 * Main RESTlet minimal example
 *
 * @author msimonin
 */
public final class Main
{

    /** Hide constructor. */
    private Main()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Main method.
     *
     * @param args  The arguments of the commande line
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
    	 // Create a component
        Component component = new Component();
        Context context = component.getContext().createChildContext();
        component.getServers().add(Protocol.HTTP, 8124);

        // Create an application
        Application application = new MyGareApplication(context);

        System.out.println("demarré1");
        // Add the backend into component's context
        Gare backend = new Gare();
        System.out.println("demarré2");
        context.getAttributes().put("backend", backend);
        System.out.println("demarré3");
        component.getDefaultHost().attach(application);

        System.out.println("demarré4");
        // Start the component
        
        component.start();
        System.out.println("demarré5");
    }

}
