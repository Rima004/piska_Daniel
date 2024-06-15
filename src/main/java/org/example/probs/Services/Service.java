package org.example.probs.Services;

import org.example.probs.objects.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public interface Service <T>{

     Connection getDBConnection();

      ResultSet Show();

      void Add(T objects);

      void Delete(int id);

      void  editDate (int id, String name_colm, String newInfo);

      List<?> filterByName(String name);



}

