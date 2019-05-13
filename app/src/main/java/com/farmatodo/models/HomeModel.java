package com.farmatodo.models;

import com.farmatodo.application.App;
import com.farmatodo.data.*;
import com.farmatodo.data.Character;
import com.farmatodo.interfaces.ApiServices;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeModel {

    private ApiServices apiServices = App.appComponent.apiService();

    private double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public Observable getRequestObservable(String exp){
        Observable apiObservable;
        int result = 13;
        try {
            result = (int) Math.round(eval(exp));
        } catch (RuntimeException ignored){}
        if (result == 0) {
            apiObservable = apiServices.getCharacters();
        } else if(isNumberMultiple(result, 3) || isNumberMultiple(result, 5)){
            apiObservable = apiServices.getComics();
        } else if(isNumberMultiple(result, 7)){
            apiObservable = apiServices.getCreators();
        } else if(isNumberMultiple(result, 11)){
            apiObservable = apiServices.getEvents();
        } else if(isNumberMultiple(result, 13)){
            apiObservable = apiServices.getSeries();
        } else {
            apiObservable = apiServices.getStories();
        }
        return apiObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable getDetailRequestObservable(BaseData data){
        if(data == null){
            throw new NullPointerException("Data can not be null");
        }
        Observable apiObservable = null;
        if (data instanceof Character) {
            apiObservable = apiServices.getCharacter(data.getId());
        } else if(data instanceof Comic){
            apiObservable = apiServices.getComic(data.getId());
        } else if(data instanceof Creator){
            apiObservable = apiServices.getCreator(data.getId());
        } else if(data instanceof Event){
            apiObservable = apiServices.getEvent(data.getId());
        } else if(data instanceof Serie){
            apiObservable = apiServices.getSerie(data.getId());
        } else if (data instanceof Story){
            apiObservable = apiServices.getStory(data.getId());
        }
        return apiObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private boolean isNumberMultiple(int n1, int n2){
        return n1 % n2 == 0;
    }

}
