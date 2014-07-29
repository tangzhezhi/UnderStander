package com.tang.understander.db;

public final class DBScript {
  	
  	public static final String CREATE_TABLE_TASKCURRENTDAY = 
      		"CREATE TABLE TaskCurrentDay " + 
              "( " + 
      				"id text not null ,"+
      				"orgId text  ,"+
      				"authorId text ,"+
      				"authorName text  ,"+
      				"type text  ,"+
      				"title text  ,"+
      				"state text  ,"+
      				"createTime text  ,"+
      				"createDate text ,"+
      				"remindType text  ,"+
      				"remindDate text , "+
      				"remindTime text  "+
              "); ";

    public static final String DROP_TABLE_TASKCURRENTDAY= "DROP TABLE IF EXISTS TaskCurrentDay; ";
}
