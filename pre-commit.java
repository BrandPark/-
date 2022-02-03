import java.io.File;

public class PreCommitHook {
	public static void main(String[] args) {
		String path = PreCommitHook.class.getResource(".").getPath();

        	System.out.println(path);
	        File dir = new File(path);
     		String[] fileNameList = dir.list();
        	for(String fn : fileNameList) {
            		System.out.println(fn);
        	}
		System.exit(1);
	}
}
