package router;

public interface Router {
	public boolean withRoute(String path, int value);
	public int route(String path);
}
