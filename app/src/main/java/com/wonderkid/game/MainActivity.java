package com.wonderkid.game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(new GameView(this));
    }

    static final class GameView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Random random = new Random();
        private final SharedPreferences save;
        private int week, energy, skill, money, goals, points, shots, matchGoals;
        private int reputation, clubLevel, consecutiveGoals;
        private boolean inMatch;
        private String message = "Kariyerine başla!";
        private float downX, downY;
        private final RectF train = new RectF(), rest = new RectF(), match = new RectF();

        GameView(Context context) {
            super(context);
            save = context.getSharedPreferences("career", Context.MODE_PRIVATE);
            week = save.getInt("week", 1); energy = save.getInt("energy", 100);
            skill = save.getInt("skill", 25); money = save.getInt("money", 100);
            goals = save.getInt("goals", 0); points = save.getInt("points", 0);
            reputation = save.getInt("reputation", 0); clubLevel = save.getInt("clubLevel", 0);
        }
        private void persist() {
            save.edit().putInt("week", week).putInt("energy", energy).putInt("skill", skill)
                .putInt("money", money).putInt("goals", goals).putInt("points", points)
                .putInt("reputation", reputation).putInt("clubLevel", clubLevel).apply();
        }
        private void rect(Canvas c, int color, float l, float t, float r, float b, float radius) {
            paint.setColor(color); c.drawRoundRect(l,t,r,b,radius,radius,paint);
        }
        private void text(Canvas c, String value, float x, float y, float size, int color, boolean bold) {
            paint.setColor(color); paint.setTextSize(size); paint.setTypeface(bold ? android.graphics.Typeface.DEFAULT_BOLD : android.graphics.Typeface.DEFAULT);
            c.drawText(value,x,y,paint);
        }
        private void button(Canvas c, RectF r, String title, String detail, int color) {
            rect(c,color,r.left,r.top,r.right,r.bottom,18);
            text(c,title,r.left+20,r.top+33,19,Color.WHITE,true);
            text(c,detail,r.left+20,r.top+57,13,0xffcfe0dc,false);
        }
        @Override protected void onDraw(Canvas raw) {
            super.onDraw(raw);
            float scale = getWidth()/360f; float h = getHeight()/scale;
            Canvas c = raw; c.save(); c.scale(scale,scale);
            c.drawColor(0xff071e1c);
            text(c,"WONDERKID",22,42,23,0xffcfff64,true);
            text(c,"OYUNCU KARİYERİ",22,62,11,0xffa6bdb6,true);
            if (!inMatch) {
                rect(c,0xff113732,18,82,342,202,20);
                text(c,"HAFTA " + week,34,113,15,0xffcfff64,true);
                text(c,clubName(),34,143,23,Color.WHITE,true);
                text(c,String.format(Locale.ROOT,"Enerji %d   •   Yetenek %d",energy,skill),34,173,14,0xffd7e7df,false);
                text(c,String.format(Locale.ROOT,"Bakiye %d   •   Gol %d   •   Puan %d",money,goals,points),22,232,13,Color.WHITE,false);
                train.set(18,253,342,322); rest.set(18,333,342,402); match.set(18,413,342,482);
                button(c,train,"ANTRENMAN","-20 enerji · +3 yetenek · 20 kredi",0xff24534a);
                button(c,rest,"DİNLEN","+35 enerji · 10 kredi",0xff24534a);
                button(c,match,"MAÇA ÇIK","3 şut · 30 enerji",0xff597d21);
                text(c,"İtibar " + reputation + "  •  Maaş " + salary() + "/maç",22,508,13,0xffcfff64,true);
                text(c,message,22,535,13,0xffd9e5a8,false);
                text(c,"İlerleme otomatik kaydedilir.",22,Math.max(570,h-25),12,0xff819e94,false);
            } else {
                text(c,"MAÇ GÜNÜ",22,100,19,Color.WHITE,true);
                text(c,"Şut " + (shots+1) + "/3    Gol " + matchGoals,22,127,15,0xffcfff64,true);
                rect(c,0xff245b42,18,150,342,Math.min(h-80,570),12);
                float goalY=190;
                paint.setColor(Color.WHITE); paint.setStyle(Paint.Style.STROKE); paint.setStrokeWidth(3);
                c.drawRect(72,goalY,288,goalY+80,paint); c.drawLine(180,goalY,180,goalY+80,paint);
                paint.setStyle(Paint.Style.FILL);
                text(c,"KALE",160,goalY+48,13,Color.WHITE,true);
                text(c,"Topu kaleye doğru kaydır",60,Math.min(h-110,500),16,Color.WHITE,true);
                text(c,message,25,Math.min(h-85,530),13,0xffe7f5bd,false);
            }
            c.restore();
        }
        private String clubName() {
            return clubLevel == 2 ? "Yıldızlar SK" : clubLevel == 1 ? "Kent Spor" : "Gençlik SK";
        }
        private int salary() { return 30 + clubLevel * 20; }
        @Override public boolean onTouchEvent(MotionEvent event) {
            float scale=getWidth()/360f, x=event.getX()/scale, y=event.getY()/scale;
            if (event.getAction()==MotionEvent.ACTION_DOWN) { downX=x; downY=y; return true; }
            if (event.getAction()!=MotionEvent.ACTION_UP) return true;
            if (!inMatch) {
                if (train.contains(x,y)) {
                    if (energy < 20 || money < 20) message="Antrenman için enerji veya kredi yetersiz.";
                    else { energy-=20; money-=20; skill=Math.min(100,skill+3); message="Antrenman tamamlandı!"; persist(); }
                } else if (rest.contains(x,y)) {
                    if (money<10) message="Dinlenmek için 10 kredi gerekli.";
                    else if (energy==100) message="Enerjin zaten dolu.";
                    else { money-=10; energy=Math.min(100,energy+35); message="Enerjin yenilendi."; persist(); }
                } else if (match.contains(x,y)) {
                    if (energy<30) message="Maç için en az 30 enerji gerekli.";
                    else { energy-=30; shots=0; matchGoals=0; consecutiveGoals=0; inMatch=true; message="İsabet için kaleye nişan al."; persist(); }
                }
            } else if (downY-y>40) {
                boolean onTarget=x>=72 && x<=288 && y>=150 && y<=300;
                boolean goal=onTarget && random.nextInt(100)<35+skill/2;
                shots++; if(goal) { matchGoals++; consecutiveGoals++; } else consecutiveGoals=0;
                message=goal ? "GOOOL!" : "Kaçtı!";
                if(shots==3) {
                    goals+=matchGoals; points+=matchGoals>=2?3:matchGoals==1?1:0;
                    money+=salary()+matchGoals*20; reputation+=matchGoals*2;
                    if (consecutiveGoals == 3) { reputation+=3; money+=30; }
                    int previousLevel=clubLevel;
                    if (reputation>=30) clubLevel=2; else if (reputation>=12) clubLevel=1;
                    week++; inMatch=false;
                    message=clubLevel>previousLevel ? "Yeni sözleşme: " + clubName() + "!" : "Maç bitti: " + matchGoals + " gol attın."; persist();
                }
            }
            invalidate(); return true;
        }
    }
}
