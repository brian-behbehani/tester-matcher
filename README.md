# tester-matcher


This is a java based implementation of the tester matcher application. It is intended to receive user input from the command line.

The project is built with ant, so you'll need ant and java to build and run.

Java 1.8 and Ant 1.10.5 were used by the author.


Sample program output: 

Please enter the search criteria. For multiple values, please separate using a comma. To choose all, enter ALL . <br />
Countries?  <br />
JP   <br />
Phones?  <br />
ALL  <br />

The following matching testers were found with: [ALL] in: [JP] <br />
Lucas Lowry=>117, Last login: 2013-07-12 23:57:38 <br />
Sean Wellington=>116, Last login: 2013-08-05 13:27:38 <br />
Mingquan Zheng=>109, Last login: 2013-08-04 22:07:38 <br />

Please enter the search criteria. For multiple values, please separate using a comma. To choose all, enter ALL. <br />
Countries? <br />
US <br />
Phones? <br />
iPhone 3, iPhone 5 <br />

The following matching testers were found with: [iPhone 3, iPhone 5] in: [US].   <br />
Miguel Bautista=>65, Last login: 2013-08-04 23:57:38 <br />



