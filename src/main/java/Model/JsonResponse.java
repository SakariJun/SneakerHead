package Model;

public class JsonResponse {

	private boolean status;
	private Object message;

	public JsonResponse(boolean status, Object message) {
		this.status = status;
		this.message = message;
	}
}
