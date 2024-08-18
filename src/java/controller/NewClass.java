/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 *
 * @author nguye
 */
public class NewClass {

    public static void main(String[] args) {
        Path defaultImg = Paths.get("D:\\New folder\\1\\SWP391.E.BL5.G5\\web\\images\\default.jpg");
        byte[] userAvatar_raw = convertPathToByteArray(defaultImg);
        String userAvatar = Base64.getEncoder().encodeToString(userAvatar_raw);
        System.out.println(userAvatar);
    }

    private static byte[] convertPathToByteArray(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();

            return new byte[0];

        }
    }
}
