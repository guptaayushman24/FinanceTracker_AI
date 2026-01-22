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
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.embedding.EmbeddingRequest;
//import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.Arrays;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public float[] imageEmbeddingVecotor(MultipartFile image) throws IOException {
        // Read the file into a BufferedImage
        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());

        BufferedImage imageCopy = new BufferedImage(
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR
        );

        imageCopy.getGraphics().drawImage(bufferedImage,0,0,null);
        byte[] data = ((DataBufferByte) imageCopy.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(imageCopy.getHeight(),imageCopy.getWidth(),CvType.CV_8UC3);

        Mat blob = Dnn.blobFromImage(
                mat,
                1.0/255.0,
                new Size(112,112),
                new Scalar(0,0,0),
                false,
                false);

        // For Windows
       //  Net net = Dnn.readNetFromONNX("D://FinanceTracker_AI//facerecognition//src//main//resources//model//model.onnx");

        // For Mac
        Net net = Dnn.readNetFromONNX("/Users/ayushmangupta/Documents/FinanceTracker_AI/facerecognition/src/main/resources/model/model.onnx");

        net.setInput(blob);
        Mat output = net.forward();

        int  dim = output.cols();
        float [] embedding = new float[dim];

        for (int i=0;i<dim;i++){
            embedding[i] = (float) output.get(0,i)[0];
        }

        // L2 Normalization
        float norm = 0f;
        for (float v:embedding){
            norm = norm+v*v;
        }
        norm = (float) Math.sqrt(norm);

        for (int i=0;i<embedding.length;i++){
            embedding[i] = embedding[i]/norm;
        }

        return embedding;
    }
}
