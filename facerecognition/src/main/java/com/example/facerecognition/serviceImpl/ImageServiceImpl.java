package com.example.facerecognition.serviceImpl;

import com.example.facerecognition.service.ImageService;
import nu.pattern.OpenCV;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.osgi.OpenCVNativeLoader;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.Arrays;

@Service
public class ImageServiceImpl implements ImageService {
    private final EmbeddingModel embeddingModel;
    public ImageServiceImpl (EmbeddingModel embeddingModel){
        this.embeddingModel = embeddingModel;
    }
    @Override
    public EmbeddingResponse imageEmbedding(String... imageString) throws IOException {
            return null;
//        EmbeddingRequest embeddingRequest = new EmbeddingRequest(Arrays.asList(imageString),null);
//        return embeddingModel.call(embeddingRequest);
    }

    @Override
    public float[] imageEmbeddingVecotor(MultipartFile image) throws IOException {
        // Read the file into a BufferedImage
        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());

        // Convert BufferedImage to a consistent type (TYPE_3BYTE_BGR is standard for OpenCV)
        BufferedImage imageCopy = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        imageCopy.getGraphics().drawImage(bufferedImage, 0, 0, null);

        // Get the pixel data
        byte[] data = ((DataBufferByte) imageCopy.getRaster().getDataBuffer()).getData();

        // Create a Mat and put the data into it

        Mat mat = new Mat(imageCopy.getHeight(), imageCopy.getWidth(), (int) CvType.CV_8UC3);

        // Preprocess (Resize & Normalize) - example for 224x224
        Mat blob = Dnn.blobFromImage(mat,1.0/255.0,new Size(244,244),new Scalar(0,0,0,0),true,false);

        // Load Model
        Net net = Dnn.readNetFromONNX("/Users/ayushmangupta/Documents/FinanceTracker_AI/facerecognition/src/main/resources/model/model.onnx");

        // Forward Pass
        net.setInput(blob);
        Mat output = net.forward(); // This mat contains the embedding

        // Extract vector (example: flatten the output Mat)
        // The output 'Mat' might need reshaping/flattening depending on the model's output layer
        float [] embedding = new float[output.cols()];
        for (int i=0;i<output.cols();i++){
            embedding[i] = (float) output.get(0,i)[0]; // Get values for each dimension
        }

        return embedding;
    }
}
