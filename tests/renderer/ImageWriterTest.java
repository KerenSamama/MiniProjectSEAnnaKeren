package renderer;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    /**
     * Test method for {@link ImageWriter#writeToImage()}
     */
    @Test
    public void writeToImage()
    {
        String imagename = "ImageWriter_Test";
        int width = 1600;
        int height = 1000;
        int nx = 800;
        int ny = 500;
        ImageWriter imageWriter = new ImageWriter(imagename, nx, ny);

        for (int i = 0; i < ny; i ++)
        {
            for (int j = 0; j < nx; j ++)
            {

                    imageWriter.writePixel(j, i, new primitives.Color(Color.pink));

            }
        }
        for (int i = 0; i < ny; i ++)
        {
            for (int j = 0; j < nx; j ++)
            {
                if (i % 50 == 0 || j % 50 == 0)
                {
                    imageWriter.writePixel(j, i, new primitives.Color(Color.WHITE));
                }
            }
        }

        imageWriter.writeToImage();
    }

}