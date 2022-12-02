/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Praktikum_6;

import Praktikum_5.*;
import Pertemuan_4.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author zaki
 */
public class EkualisasiHistogram extends javax.swing.JFrame {

    String sumber;

    /**
     * Creates new form NewJFrame
     */
    public EkualisasiHistogram() {
        initComponents();
    }

    public static BufferedImage loadImage(String ref) {
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(new File(ref));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimg;
    }

    // Deklarasi Methode untuk menyesuaikan ukuran gambar dengan ukuran jLabel
    public static BufferedImage resize(BufferedImage img,
            int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH,
                img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }

    // deklarasi methode untuk grayscale
    public BufferedImage rgb2Gray(String sumber) {
        BufferedImage prosesGambar;
        BufferedImage loadIng = loadImage(sumber);
        int ukuranX = loadIng.getWidth();
        int ukuranY = loadIng.getHeight();
        prosesGambar = new BufferedImage(ukuranX, ukuranY,
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = prosesGambar.getGraphics();
        g.drawImage(loadIng, 0, 0, null);
        WritableRaster raster = prosesGambar.getRaster();
        for (int x = 0; x < ukuranX; x++) {
            for (int y = 0; y < ukuranY; y++) {
                int rgb = loadIng.getRGB(x, y);
//                int alpha = (rgb << 24) & 0xff;
                int merahg = (rgb >> 16) & 0xff;
                int hijaug = (rgb >> 8) & 0xff;
                int birug = (rgb >> 0) & 0xff;
                float gray = (float) ((0.5 * merahg) + (0.3 * hijaug) + (0.2 * birug));
                raster.setSample(x, y, 0, gray);
            }
        }
        return prosesGambar;
    }

    public void drawHistogram(BufferedImage gmbar, String hs1) {
        int widht = gmbar.getWidth();
        int height = gmbar.getHeight();
        int count[][] = new int[4][0x100];
        int RED = 0;
        int BLUE = 1;
        int GREEN = 2;
        int total = widht * height;
        for (int x = 0; x < widht; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = gmbar.getRGB(x, y);
                int merahg = (rgb & 0x00ff0000) >> 16;
                int hijaug = (rgb & 0x0000ff00) >> 8;
                int birug = (rgb & 0x000000ff);
                int gray = (merahg + hijaug + birug) / 3;
                count[RED][merahg]++;
                count[GREEN][hijaug]++;
                count[BLUE][birug]++;
                count[3][gray]++;
            }
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int f = 0; f < 0x100; f++) {
            dataset.addValue(count[0][f], "Red", new Integer(f));
            dataset.addValue(count[2][f], "Blue", new Integer(f));
            dataset.addValue(count[1][f], "Green", new Integer(f));
            dataset.addValue(count[3][f], "Black", new Integer(f));
        }
        JFreeChart histo = ChartFactory.createBarChart("Histogram Citra",
                "Nilai", "Frekuensi", dataset, PlotOrientation.VERTICAL, true, false, false);
        ChartFrame frame = new ChartFrame("histogram Citra", histo);
        histo.setBackgroundPaint(Color.white);
        final CategoryPlot plot = (CategoryPlot) histo.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeMinorGridlinePaint(Color.white);
        frame.setSize(500, 350);
        frame.setVisible(true);
        frame.setLocation(380, 200);
        frame.setTitle(hs1);
        String status = frame.getTitle();
    }

    public BufferedImage brigthness(String sumber, int cerah) {
        BufferedImage prosesGambar;
        BufferedImage loadIng = loadImage(sumber);
        int ukuranX = loadIng.getWidth();
        int ukuranY = loadIng.getHeight();
        prosesGambar = new BufferedImage(ukuranX, ukuranY,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = prosesGambar.getGraphics();
        g.drawImage(loadIng, 0, 0, null);
        WritableRaster raster = prosesGambar.getRaster();
        for (int x = 0; x < ukuranX; x++) {
            for (int y = 0; y < ukuranY; y++) {
                int rgb = loadIng.getRGB(x, y);
                int alpha = (rgb << 24) & 0xff;
                int merahg = (rgb >> 16) & 0xff;
                int hijaug = (rgb >> 8) & 0xff;
                int birug = (rgb >> 0) & 0xff;
                int gray = ((merahg) + (hijaug) + (birug)) / 3;
                int merah2 = merahg + cerah;
                int hijau2 = hijaug + cerah;
                int biru2 = birug + cerah;
                if (merah2 < 0) {
                    merah2 = 0;
                }
                if (hijau2 < 0) {
                    hijau2 = 0;
                }
                if (biru2 < 0) {
                    biru2 = 0;
                }
                if (merah2 > 255) {
                    merah2 = 255;
                }
                if (hijau2 > 255) {
                    hijau2 = 255;
                }
                if (biru2 > 255) {
                    biru2 = 255;
                }
                int rgb2 = alpha | merah2 << 16 | hijau2 << 8 | biru2;
                prosesGambar.setRGB(x, y, rgb2);
            }
        }
        return prosesGambar;
    }

    public BufferedImage kontras(String sumber, int kontras) {

        BufferedImage prosesGambar;
        BufferedImage loadIng = loadImage(sumber);
        int ukuranX = loadIng.getWidth();
        int ukuranY = loadIng.getHeight();
        prosesGambar = new BufferedImage(ukuranX, ukuranY,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = prosesGambar.getGraphics();
        g.drawImage(loadIng, 0, 0, null);
        WritableRaster raster = prosesGambar.getRaster();
        for (int x = 0; x < ukuranX; x++) {
            for (int y = 0; y < ukuranY; y++) {
                int rgb = loadIng.getRGB(x, y);
                int alpha = (rgb << 24) & 0xff;
                int merahg = (rgb >> 16) & 0xff;
                int hijaug = (rgb >> 8) & 0xff;
                int birug = (rgb >> 0) & 0xff;
                int gray = ((merahg) + (hijaug) + (birug)) / 3;
                int merah2 = 0, hijau2 = 0, biru2 = 0;
                if (merahg > 128) {
                    merah2 = merahg + kontras;
                } else {
                    merah2 = merahg - kontras;
                }
                if (hijaug > 128) {
                    hijau2 = hijaug + kontras;
                } else {
                    hijau2 = hijaug - kontras;
                }
                if (birug > 128) {
                    biru2 = birug + kontras;
                } else {
                    biru2 = birug + kontras;
                }
                if (merah2 < 0) {
                    merah2 = 0;
                }
                if (hijau2 < 0) {
                    hijau2 = 0;
                }
                if (biru2 < 0) {
                    biru2 = 0;
                }
                if (merah2 > 255) {
                    merah2 = 255;
                }
                if (hijau2 > 255) {
                    hijau2 = 255;
                }
                if (biru2 > 255) {
                    biru2 = 255;
                }
                int rgb2 = alpha | merah2 << 16 | hijau2 << 8 | biru2;
                prosesGambar.setRGB(x, y, rgb2);
            }
        }
        return prosesGambar;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tampil_input = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tampil_output = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollBar2 = new javax.swing.JScrollBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        view = new javax.swing.JMenu();
        histogram = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Citra Input");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Citra Output");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tampil_input.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tampil_input, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tampil_input, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tampil_output.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tampil_output, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tampil_output, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
        );

        jLabel3.setText("Pengaturan Brightness");

        jLabel4.setText("Pengaturan Contrast");

        jScrollBar1.setMaximum(255);
        jScrollBar1.setMinimum(-255);
        jScrollBar1.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
        jScrollBar1.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                jScrollBar1AdjustmentValueChanged(evt);
            }
        });

        jScrollBar2.setMaximum(128);
        jScrollBar2.setMinimum(-128);
        jScrollBar2.setOrientation(javax.swing.JScrollBar.HORIZONTAL);
        jScrollBar2.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                jScrollBar2AdjustmentValueChanged(evt);
            }
        });

        jMenu1.setText("File");

        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Prepropcessing");

        jMenuItem2.setText("Konversi Gray Scale");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Ekualisasi Histogram");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        view.setText("View");

        histogram.setSelected(true);
        histogram.setText("histogram");
        histogram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                histogramActionPerformed(evt);
            }
        });
        view.add(histogram);

        jMenuBar1.add(view);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(107, 107, 107)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(22, 22, 22)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JFileChooser jfc = new JFileChooser();
        int open = jfc.showOpenDialog(null);
        if (open == JFileChooser.APPROVE_OPTION) {
            sumber = jfc.getSelectedFile().getPath();
            BufferedImage loadImg = loadImage(sumber);
            ImageIcon imgIcon1 = new ImageIcon(resize(loadImg, tampil_input.getWidth(), tampil_input.getHeight()));
            ImageIcon imgIcon2 = new ImageIcon(resize(loadImg, tampil_output.getWidth(), tampil_output.getHeight()));

            tampil_input.setIcon(imgIcon1);
            tampil_output.setIcon(imgIcon2);

//            ImageIcon imgIcon1 = new ImageIcon(loadImg, tampil_input.getWidth(), tampil_input.getHeight());
//            ImageIcon imgIcon2 = new ImageIcon(loadImg, tampil_output.getWidth(), tampil_output.getHeight());
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        BufferedImage bi = rgb2Gray(sumber);
        ImageIcon imageIcon = new ImageIcon(resize(bi, tampil_output.getWidth(), tampil_output.getHeight()));
        tampil_output.setIcon(imageIcon);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void histogramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_histogramActionPerformed
        // TODO add your handling code here:
        BufferedImage loadImg = loadImage(sumber);
        drawHistogram(loadImg, sumber);
    }//GEN-LAST:event_histogramActionPerformed

    private void jScrollBar1AdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {//GEN-FIRST:event_jScrollBar1AdjustmentValueChanged
        // TODO add your handling code here:
        int cerah = jScrollBar1.getValue();
        BufferedImage bi = brigthness(sumber, cerah);
        ImageIcon imageicon = new ImageIcon(resize(bi, tampil_output.getWidth(), tampil_output.getHeight()));
        tampil_output.setIcon(imageicon);

    }//GEN-LAST:event_jScrollBar1AdjustmentValueChanged

    private void jScrollBar2AdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {//GEN-FIRST:event_jScrollBar2AdjustmentValueChanged
        // TODO add your handling code here:
        int kont = jScrollBar1.getValue();
        BufferedImage ctrs = kontras(sumber, kont);
        ImageIcon imageicon = new ImageIcon(resize(ctrs, tampil_output.getWidth(), tampil_output.getHeight()));
        tampil_output.setIcon(imageicon);
    }//GEN-LAST:event_jScrollBar2AdjustmentValueChanged

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        HistogrEqualisation histEq = new HistogrEqualisation();
        BufferedImage gbInput = loadImage(sumber);
        BufferedImage gbEq;
        int ukuranX = gbInput.getWidth();
        int ukuranY = gbInput.getHeight();
        int size = ukuranX * ukuranY;
        int grayScale[][] = new int[ukuranX][ukuranY];
        int histogram[] = new int[256];
        int cdf[] = new int[256];
        float equalized[] = new float[256];
        float picEqualized[][] = new float[ukuranX][ukuranY];
        grayScale = histEq.ArrayGray(sumber);
        histogram = histEq.histogram(grayScale, ukuranX, ukuranY);
        cdf = histEq.getCDF(histogram);
        equalized = histEq.equalization(cdf, size);
        picEqualized = histEq.gbEqualiz(grayScale, equalized, ukuranX, ukuranY);
        gbEq = histEq.gbHasil(picEqualized, ukuranX, ukuranY);
        int counter = 0;
        int x = jLabel2.getWidth();
        int y = jLabel2.getHeight();
        ImageIcon imageIcon = new ImageIcon(resize(gbEq, x, y));
        jLabel2.setIcon(imageIcon);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EkualisasiHistogram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EkualisasiHistogram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EkualisasiHistogram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EkualisasiHistogram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EkualisasiHistogram().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButtonMenuItem histogram;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JLabel tampil_input;
    private javax.swing.JLabel tampil_output;
    private javax.swing.JMenu view;
    // End of variables declaration//GEN-END:variables
}
