package com.mistura_boa.mistura_boa.services;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.mistura_boa.mistura_boa.models.enums.FormaPagamentoEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImpressaoService {

    private final PedidoService pedidoService;

    public ResponseEntity<byte[]> imprimirPedido(Long idPedido) throws Exception {
        var pedido = pedidoService.getById(idPedido);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PageSize cupomSize = new PageSize(new Rectangle(226.77f, 3000f));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, cupomSize);
        document.setFontSize(12);

        

        try {
            InputStream logoStream = getClass().getResourceAsStream("/imgs/logo.jpg");
            byte[] imageBytes = logoStream.readAllBytes();
            ImageData imageData = ImageDataFactory.create(imageBytes);
            Image logo = new Image(imageData).setTextAlignment(TextAlignment.CENTER).scaleToFit(100, 100).setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(logo);
        } catch (Exception e) {
            // opcional: ignorar se não encontrar a imagem
        }

        document.add(new Paragraph("Pedido")
                .setFontSize(20) // maior
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));

        document.add(new Paragraph("Número: " + pedido.getNumeroPedido().toString()).setMarginBottom(5));
        document.add(new Paragraph("Cliente: " + pedido.getUsuario().getPessoa().getNome()).setFontSize(12).setMarginBottom(5));
        LocalDateTime dataPedido = pedido.getDataPedido();
        String dataFormatada = dataPedido.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        document.add(new Paragraph("Data: " + dataFormatada).setMarginBottom(10));


        document.add(new Paragraph("Produtos")
        .setFontSize(16)
        .setBold()
        .setTextAlignment(TextAlignment.CENTER)
        .setMarginBottom(10));
        
        Table table = new Table(UnitValue.createPercentArray(new float[]{60, 30, 10})).useAllAvailableWidth();

        Cell produto = new Cell().add(new Paragraph("Produto")).setBorder(Border.NO_BORDER);
        Cell tamanho = new Cell().add(new Paragraph("Tamanho")).setBorder(Border.NO_BORDER);
        Cell valor = new Cell().add(new Paragraph("Valor")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);

        table.addHeaderCell(produto);
        table.addHeaderCell(tamanho);
        table.addHeaderCell(valor);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        for(var produtoPedido:pedido.getProdutosPedido()){
            Cell Nomeproduto = new Cell().add(new Paragraph(produtoPedido.getProduto().getCategoria().getNome() + " " + produtoPedido.getProduto().getNome())).setBorder(Border.NO_BORDER);
            Cell tamanhoProd = new Cell().add(new Paragraph(produtoPedido.getTamanhoMomentoCompra() == null ? "" : produtoPedido.getTamanhoMomentoCompra())).setBorder(Border.NO_BORDER);
            Cell valorProd = new Cell().add(new Paragraph(formatter.format(produtoPedido.getValorMomentoCompra()))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
            
            table.addCell(Nomeproduto);
            table.addCell(tamanhoProd);
            table.addCell(valorProd);

            if(produtoPedido.getObservacao() != null){
                Text label = new Text("Obs: ").setBold();
                Text texto = new Text(produtoPedido.getObservacao());
                Cell observacao = new Cell(1,3).add(new Paragraph().add(label).add(texto).setFontSize(10)).setBorder(Border.NO_BORDER);
                table.addCell(observacao);
            }

        }

        document.add(table);
        
        document.add(new Paragraph("TOTAL: " + formatter.format(pedido.getValor())).setFontSize(14).setBold().setTextAlignment(TextAlignment.RIGHT).setMarginTop(20));

        document.add(new Paragraph("Forma de pagamento")
        .setFontSize(16)
        .setBold()
        .setTextAlignment(TextAlignment.CENTER)
        .setMarginBottom(10));

        document.add(new Paragraph(pedido.getFormaPagamento().getDescricao()).setMarginBottom(1));

        if(pedido.getFormaPagamento() == FormaPagamentoEnum.DINHEIRO){
            if(pedido.getPrecisaTroco()){
                document.add(new Paragraph("Troco para: " + formatter.format(pedido.getValorTroco())).setMarginBottom(10));
            }else{
                document.add(new Paragraph("Não precisa de troco").setMarginBottom(10));
            }
        }

        document.add(new Paragraph("Entrega")
        .setFontSize(16)
        .setBold()
        .setTextAlignment(TextAlignment.CENTER)
        .setMarginBottom(10));

        if(pedido.getEndereco()==null){
            document.add(new Paragraph("Pedido será retirado no estabelecimento").setMarginBottom(10));
        }else{
            var endereco = pedido.getEndereco();
            document.add(new Paragraph("Endereço para entrega: " + endereco.getLogradouro() + " - " +  endereco.getBairro() + ", n° "  + endereco.getNumero()).setMarginBottom(1));
            document.add(new Paragraph("Ponto de referência: " + (endereco.getPontoReferencia()== null ? "Não possui ponto de referência" : endereco.getPontoReferencia())).setMarginBottom(1));
            document.add(new Paragraph("Telefone para contato: " + pedido.getUsuario().getPessoa().getTelefone()).setMarginBottom(10));
        }

        document.close();
        byte[] pdfBytes = out.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "pedido_" + pedido.getNumeroPedido() + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
