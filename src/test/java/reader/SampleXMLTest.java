package reader;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import model.StatusItem;
import reader.AwsStatusReaderConfig.ReaderType;

public class SampleXMLTest {

	
	@Test
	public void testReader() throws Exception
	{
		AwsStatusReaderConfig config = AwsStatusReaderConfig.builder()
			.url("src/test/resources/sampleXML.xml")
			.sourceType(ReaderType.FILE)
			.build();
			
		
		AwsStatusReader reader = new AwsStatusReader(config);
		
		
		List<StatusItem> items = reader.loadStatus();
		
		assertEquals("Expected num items", 15, items.size());
	}
	
}
