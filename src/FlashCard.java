
public class FlashCard {
	//instance variables
	private String question;
	private String answer;

	//constructor
	public FlashCard(String q, String a) {
		question = q;
		answer = a;
	}
	
	//setters and getters to call our variables
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
