package com.rest.web.inka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;

import com.rest.web.inka.models.Producto;

import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.*;

@Service
public class PdfService {
    
   private static final String PDF_RESOURCES = "/pdf-resources/";

    private final org.thymeleaf.spring6.SpringTemplateEngine springTemplateEngine;
    private final ProductoService productoService;

    @Autowired
    public PdfService(org.thymeleaf.spring6.SpringTemplateEngine springTemplateEngine, ProductoService productoService) {
        this.springTemplateEngine = springTemplateEngine;
        this.productoService = productoService;
    }

    public File generateProductoPdf() throws Exception{
        Context context = getContextProductoListPdf();
        String html = loadAndFillTemplate(context);
        String xhtml = convertToXhtml(html);
        return renderProductoListPdf(xhtml);
    }

    private String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setXHTML(true);
        tidy.setIndentContent(true);
        tidy.setPrintBodyOnly(true);
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setSmartIndent(true);
        tidy.setShowWarnings(false);
        tidy.setQuiet(true);
        tidy.setTidyMark(false);

        org.w3c.dom.Document htmlDOM = tidy.parseDOM(new ByteArrayInputStream(html.getBytes()), null);

        OutputStream out = new ByteArrayOutputStream();
        tidy.pprint(htmlDOM, out);
        return out.toString();
    }

    private File renderProductoListPdf(String html) throws Exception {
        File file = File.createTempFile("productos", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    private Context getContextProductoListPdf() {
        List<Producto> productos = productoService.list();
        Context context = new Context();
        context.setVariable("productos", productos);
        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return springTemplateEngine.process("report_stock", context);
    }

}