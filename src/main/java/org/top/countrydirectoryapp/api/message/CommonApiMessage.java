package org.top.countrydirectoryapp.api.message;

public class CommonApiMessage {

    public record ServerStatus(String status, String host, String protocol){  }

    public record StringMessage(String message){   }

    public record ErrorMessage(String code, String details){   }
}
