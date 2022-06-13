/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysnakegame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author Oziron
 */
public class Yilan extends JLabel{

    public Kutu mHead = new Kutu();
    public Yem mYem = new Yem();
    public Timer mTimer = null;
    public Random mRandom = null;
    public ArrayList <Kutu> Liste = new ArrayList <Kutu>();
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        //System.out.println(getWidth());
        
        Graphics2D g2 = (Graphics2D)g;
        
        Rectangle2D rect = new Rectangle2D.Double(5, 5, getWidth()-15, getHeight()-10);
        
        g2.setColor(Color.red);
        
        g2.setStroke(new BasicStroke(10));
        
        g2.draw(rect);
    }
    
    Yilan() 
    {
        mRandom = new Random(System.currentTimeMillis());
        addKeyListener(new KlavyeKontrol());
        setFocusable(true);
        
        
        mTimer = new Timer(150,new TimerKontrol());
        mTimer.start();
        
        Liste.add(mHead);
        for(int i=1;i<11;i++)
        {
            KuyrukEkle();
        }
        add(mYem);
        add(mHead);
    }
    
    public void KuyrukEkle()
    {
        Kutu K = Liste.get(Liste.size()-1).KutuOlustur();
            
        Liste.add(K);
        add(K);
            
    }
    public void YemEkle()
    {
        int Width = getWidth()-30-mYem.mGenislik;
        int Height = getHeight()-30-mYem.mGenislik;
        
        int PosX = 10+Math.abs(mRandom.nextInt())%Width;
        int PosY = 10+Math.abs(mRandom.nextInt())%Height;
        
        PosX -= PosX % 20-5;
        PosY -= PosY % 20-5;
        if(PosX<=30)
            PosX += 20;
        else if(PosX >= getWidth()-30)
            PosX -= 20;
        else if(PosY<=30)
            PosY += 20;
        else if(PosY >= getHeight()-30)
            PosY -= 20;
        
        for(int i=0;i<Liste.size();i++)
        {
            if(PosX == Liste.get(i).getX()&&(PosY==Liste.get(i).getY()))
            {
                YemEkle();
                return;
            }
        }
        
        mYem.setPosition(PosX, PosY);
    }
    public void HepsiniYurut()
    {
        for(int i=Liste.size()-1;i>0;i--)
        {
            Kutu Onceki = Liste.get(i-1);
            Kutu Sonraki= Liste.get(i);
            
            
            Liste.get(i).Hareket();
            Sonraki.mYon = Onceki.mYon;
        }
        mHead.Hareket();
    }
    public boolean CarpismaVarmi()
    {
        int Kalem = 10;
        int genislik = getWidth();
        int yukseklik = getHeight();
        if(mHead.getX()<=10 || mHead.getX()+mHead.mGenislik>=genislik-15)
            return true;
        if(mHead.getY()<=10 || mHead.getY()+(2*mHead.mGenislik)>=genislik-10)
            return true;
        
        for(int i=1;i<Liste.size()-1;i++)
        {
            int X = Liste.get(i).getX();
            int Y = Liste.get(i).getY();
            
            if((X == mHead.getX()) && (Y==mHead.getY()))
            {
                return true;
            }
            if((mYem.getX()==mHead.getX())&&(mYem.getY() == mHead.getY()))
            {
                KuyrukEkle();
                YemEkle();
            }
        }
        return false;
    }
    class KlavyeKontrol implements KeyListener
    {

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_LEFT)
            {
                if(mHead.mYon != YON.SAG)
                mHead.mYon = YON.SOL;
            }    
            if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                if(mHead.mYon != YON.SOL)
                mHead.mYon = YON.SAG;
            }    
            if(e.getKeyCode() == KeyEvent.VK_UP)    
            {
                if(mHead.mYon != YON.ASAGI)
                mHead.mYon = YON.YUKARI;
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN)
            {
                if(mHead.mYon != YON.YUKARI)
                mHead.mYon = YON.ASAGI;
            } 
                
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
        
    }
    
    class TimerKontrol implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            HepsiniYurut();
            if(CarpismaVarmi())
                mTimer.stop();
            
        }
        
    }
}
