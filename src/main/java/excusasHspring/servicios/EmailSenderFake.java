package excusasHspring.servicios;

import org.springframework.stereotype.Component;

@Component
public class EmailSenderFake implements IEmailSender {

    private String ultimoDestinatario;
    private String ultimoRemitente;
    private String ultimoAsunto;
    private String ultimoCuerpo;

    @Override
    public void enviarEmail(String destinatario, String remitente, String asunto, String cuerpo) {
        this.ultimoDestinatario = destinatario;
        this.ultimoRemitente = remitente;
        this.ultimoAsunto = asunto;
        this.ultimoCuerpo = cuerpo;

        System.out.println("Email simulado enviado:");
        System.out.println("- Para: " + destinatario);
        System.out.println("- Desde: " + remitente);
        System.out.println("- Asunto: " + asunto);
        System.out.println("- Cuerpo: " + cuerpo);
    }

    public String getUltimoDestinatario() {
        return ultimoDestinatario;
    }

    public String getUltimoRemitente() {
        return ultimoRemitente;
    }

    public String getUltimoAsunto() {
        return ultimoAsunto;
    }

    public String getUltimoCuerpo() {
        return ultimoCuerpo;
    }
}
