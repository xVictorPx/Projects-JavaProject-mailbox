public class EmailMessage {
  private String to;
  private String subject;
  private String message;

  public EmailMessage(String to, String subject, String message) {
    this.to = to;
    this.subject = subject;
    this.message = message;
  }

  public String getTo() {
    return to;
  }

  public String getSubject() {
    return subject;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return subject;
  }
}
