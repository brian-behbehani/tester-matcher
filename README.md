# tester-matcher


This is a java based implementation of the tester matcher application. It is intended to receive user input from the command line.

The project is built with ant, so you'll need ant and java to build and run.

Java 1.8 and Ant 1.10.5 were used by the author.


Sample program output: 

Please enter the search criteria. For multiple values, please separate using a comma. To choose all, enter ALL
Countries?
JP
Phones?
ALL

The following matching testers were found with: [ALL] in: [JP]
Lucas Lowry=>117, Last login: 2013-07-12 23:57:38
Sean Wellington=>116, Last login: 2013-08-05 13:27:38
Mingquan Zheng=>109, Last login: 2013-08-04 22:07:38

Please enter the search criteria. For multiple values, please separate using a comma. To choose all, enter ALL
Countries?
US
Phones?
iPhone 3, iPhone 5

The following matching testers were found with: [iPhone 3, iPhone 5] in: [US]
Miguel Bautista=>65, Last login: 2013-08-04 23:57:38

Please enter the search criteria. For multiple values, please separate using a comma. To choose all, enter ALL
Countries?
US
Phones?
ALL

The following matching testers were found with: [ALL] in: [US]
Taybin Rutkin=>125, Last login: 2013-01-01 10:57:38
Miguel Bautista=>114, Last login: 2013-08-04 23:57:38
Michael Lubavin=>99, Last login: 2013-07-12 13:27:18

Please enter the search criteria. For multiple values, please separate using a comma. To choose all, enter ALL
Countries?
