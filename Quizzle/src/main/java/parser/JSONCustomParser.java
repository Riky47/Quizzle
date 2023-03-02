package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONCustomParser {
	
	public static String sp = File.separator;
	private final String path;
	
	public JSONCustomParser(String mainPath) {
		this.path = root()+sp+mainPath;
	}
	
	public static String root(){
		File file = new File(".").getAbsoluteFile();
		File root = file.getParentFile();
		while (root.getParentFile() != null){
		    root = root.getParentFile();
		}
		return root.getPath();
	}
	
	
	public void toJson(String fileName, JSONObject obj) throws IOException {
		String folder = "Answer";
		File d = new File(path+sp+folder+sp, fileName+".json");
		d.getParentFile().mkdirs();
		d.createNewFile();
		try (FileWriter file = new FileWriter(path+sp+folder+sp+fileName+".json")) {
			System.out.println("File Saved");
            file.write(obj.toJSONString());
            file.flush();
        } 
		catch (IOException e){
			System.out.println("[EXCEPTION]");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject fromJson(String fileName) {
		String folder = "Answer";
		JSONParser jsonParser = new JSONParser();
		
		try (FileReader reader = new FileReader(path+sp+folder+sp+fileName+".json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONObject jsonObj = (JSONObject) obj;
            return jsonObj;
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return null;
	}

}
