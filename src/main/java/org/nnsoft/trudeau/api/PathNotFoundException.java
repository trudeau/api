package org.nnsoft.trudeau.api;

public final class PathNotFoundException
    extends GraphException
{

    private static final long serialVersionUID = 2919520319054603708L;

    public PathNotFoundException( String messagePattern, Object...arguments )
    {
        super( messagePattern, arguments );
    }

}
