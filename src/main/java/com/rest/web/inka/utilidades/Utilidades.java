package com.rest.web.inka.utilidades;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.rest.web.inka.models.ApiError;

public class Utilidades {
	public static ResponseEntity<Object> generateResponse(HttpStatus status, String mensaje) {
		List<String> details = new ArrayList<String>();
		details.add(mensaje);

		try {

			ApiError err = new ApiError(new Date(), HttpStatus.BAD_REQUEST, "ERROR", details);

			return new ResponseEntity<Object>(err, status);

		} catch (Exception e) {
			ApiError err = new ApiError(new Date(), HttpStatus.BAD_REQUEST, "ERROR", details);

			return new ResponseEntity<Object>(err, status);
		}
	}

	public static ResponseEntity<Object> generateResponseTrue(HttpStatus status, String mensaje) {
		List<String> details = new ArrayList<String>();
		details.add("");

		try {

			ApiError err = new ApiError(new Date(), HttpStatus.CREATED, mensaje, details);

			return new ResponseEntity<Object>(err, status);

		} catch (Exception e) {
			ApiError err = new ApiError(new Date(), HttpStatus.CREATED, mensaje, details);

			return new ResponseEntity<Object>(err, status);
		}
	}

	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
	private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

	public static String getUrl(String nombre) {

		String nowwhitespace = WHITESPACE.matcher(nombre).replaceAll("-");
		String normalized = Normalizer.normalize(nowwhitespace, Normalizer.Form.NFD);
		String url = NONLATIN.matcher(normalized).replaceAll("");
		url = EDGESDHASHES.matcher(url).replaceAll("");
		return url.toLowerCase(Locale.ENGLISH);
	}

	public static String guardarArchivo(MultipartFile multipart) {
				
		if(Utilidades.validaImagen(multipart.getContentType())== false) {
			
			return "no";
		}else {
			
			long time = System.currentTimeMillis();
			String nombre = time+Utilidades.getExtension(multipart.getContentType());
			
			Path rutaArchivo = Paths.get("imagenes").resolve(nombre).toAbsolutePath();
			
			try {
				
				//File imagenFile = new File(ruta+nombre);
				//multipart.transferTo(imagenFile);
				
				Files.copy(multipart.getInputStream(), rutaArchivo);
				
				return nombre;
			}catch (Exception e) {
				return null;
			}
		}
	}

	public static boolean validaImagen(String mime) {

		boolean retorno = false;
		switch (mime) 
		{
			case "image/jpeg": retorno = true; break;
			case "image/jpg": retorno = true; break;
			case "image/png": retorno = true; break;
			default: retorno = false; break;
		}

		return retorno;
	}
	
	public static String getExtension(String mime) {
		String retorno = "";
		switch (mime) 
		{
			case "image/jpeg": retorno = ".jpg"; break;
			case "image/jpg": retorno = ".jpg"; break;
			case "image/png": retorno = ".png"; break;
		}

		return retorno;
	}
}
