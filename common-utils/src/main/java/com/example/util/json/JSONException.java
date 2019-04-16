
package com.example.util.json;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class JSONException extends NestableRuntimeException {
   private static final long serialVersionUID = 6995087065217051815L;

   public JSONException() {
      super();
   }

   public JSONException( String msg ) {
      super( msg, null );
   }

   public JSONException( String msg, Throwable cause ) {
      super( msg, cause );
   }

   public JSONException( Throwable cause ) {
      super( (cause == null ? null : cause.toString()), cause );
   }
}