package com.damytec.service;

import com.damytec.pojo.Cpf;
import com.damytec.util.StringUtil;

/**
 * @author lgdamy@raiadrogasil.com on 22/01/2021
 */
public class GeradorService {

    private static final char ZERO = '0';
    private static GeradorService INSTANCE;

    private GeradorService() {}

    public static GeradorService getInstance() {
        return INSTANCE = INSTANCE == null ? new GeradorService() : INSTANCE;
    }

    public Cpf calcularDV(String cpf) {
        if (!StringUtil.isNumeric(cpf) || cpf.length() > 9) {
            return new Cpf();
        }
        cpf = StringUtil.leftPad(cpf,9, ZERO);
        StringBuilder sb = new StringBuilder();
        int sm = 0;
        int peso = 10;
        int num;
        for (int i=0; i<9; i++) {
            num = (cpf.charAt(i) - ZERO);
            sm = sm + (num * peso);
            peso = peso - 1;
        }
        int r = 11 - (sm % 11);
        sb.append((r>=10) ? ZERO : (char)(r+ ZERO));
        cpf = cpf.concat(sb.toString());

        sm = 0;
        peso = 11;
        for(int i=0; i<10; i++) {
            num = (cpf.charAt(i) - ZERO);
            sm = sm + (num * peso);
            peso = peso - 1;
        }

        r = 11 - (sm % 11);
        sb.append((r>=10) ? ZERO : (char)(r+ ZERO));
        cpf = cpf.concat(String.valueOf((r>=10) ? ZERO : (char)(r+ ZERO)));
        Cpf pojo = new Cpf();
        pojo.setDv(sb.toString());
        pojo.setRaw(cpf);
        pojo.setFormatted(cpf.substring(0,3)+"."+cpf.substring(3,6)+"."+cpf.substring(6,9)+"-"+cpf.substring(9));
        pojo.setValid(true);
        return pojo;
    }
}
