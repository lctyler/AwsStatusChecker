package util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;

public class S3StringReader
{
	public static String getString(AmazonS3 s3, String bucket, String key, boolean required) throws IOException
	{
		S3Object object = null;
		try
		{
			object = s3.getObject(bucket, key);
		} catch (AmazonS3Exception e)
		{
			if (!e.getErrorCode().equals("NoSuchKey") && !required)
			{
				throw e;
			}
		}

		InputStream objectData = object.getObjectContent();

		try
		{
			return IOUtils.toString(objectData);

		} finally
		{
			objectData.close();
		}
	}
}
