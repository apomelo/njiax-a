##############################################################################
#                                                                            #
#             NJIAX (Nomasystems Java IAX2 Protocol) vs 1.0.0                #
#                                                                            #
##############################################################################


0) Index

 1) Introduction
 
 2) Installation

 3) License

 4) Contact


1) Introduction

 - This project consists in the implementation in Java of the IAX2 protocol for doing VoIP calls.
 
 - The implementation is centered in voice calls, but it's easily extensible for video calls.

 - See the IAX2 draft for further information and the javadoc of the project.


2) Installation

 - In order to build the project you need to have installated Ant 1.6.5 and Java 1.5.0.
	
 - Also, you have to implement the audio system that you want to use with the IAX2 protocol (see the draft for supported audio codecs) according to the interfaces in the iax.audio package.

 - Commands:

    - ant all       -> For executing all tasks (compile, jar and javadoc)

    - ant clean     -> For deleting the directories: classes, jar and javadoc

    - ant compile   -> For compiling the source (its the default task)

    - ant jar       -> For creating the jar library

    - ant javadoc   -> For creating the javadoc 


3) License

 - See the license agreement in the file LICENSE.txt	


4) Contact
 
 - NomaSystems S.L. 
 
 - Site:    http://www.nomasystems.com
 
 - Mail:    info@nomasystems.com
 
 - Project: http://code.google.com/p/njiax

 
