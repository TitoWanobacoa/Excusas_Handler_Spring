package excusasHspring.modelo.servicios;

public interface IEmailSender {
    void enviarEmail(String destinatario, String remitente, String asunto, String cuerpo);
}
