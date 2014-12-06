<?xml version="1.0" encoding="UTF-8"?>
<!--
    Document   : overview.xsl
    Created on : 5. prosinec 2014, 22:30
    Author     : Daniel Kec
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet
    version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:atom="http://www.w3.org/2005/Atom"
    xmlns:dc="http://purl.org/dc/elements/1.1/">

    <xsl:output method="html" indent="yes"/>

    <xsl:template match="text()"></xsl:template>

    <xsl:template match="overViewBean">
        <html>
            <head>
                <style>
                    <![CDATA[
                    body {
                    text-align: center;
                    font-family:georgia, verdana, serif;
                    background-color: #E0E0E0 ;
                    }

                    #container {
                    margin: 2 auto;
                    width: 800px;
                    text-align: left;
                    background-color: white;
                    padding: 20px;
                    }
                ]]>
                    }
                </style>
                <title>
                    <xsl:value-of select="title"/>
                </title>
            </head>
        </html>
        <body>
            <h1>
                <headlines>
                    <xsl:value-of select="title"/>
                </headlines>
            </h1>
            <xsl:for-each select="combinedFeedList">
                <div id="container">
                    <div style="float: right;
                    background-color: grey;
                            width: 100px;">
                        tady bude edit
                    </div>
                    <div style="overflow: hidden;">
                        <xsl:number value="position()" format="1" />
                        <xsl:text> </xsl:text>
                        <b><xsl:value-of select="name"/></b>
                        <xsl:text> </xsl:text>
                        <a onclick='window.open(document.URL+"/html/{name}","_self")' href="javascript:void(0);">
                            <xsl:text>HTML</xsl:text>
                        </a>
                        <xsl:text>, </xsl:text>
                        <a onclick='window.open(document.URL+"/json/{name}","_self")' href="javascript:void(0);">
                            <xsl:text>JSON</xsl:text>
                        </a>
                        <xsl:text>, </xsl:text>
                        <a onclick='window.open(document.URL+"/atom/{name}","_self")' href="javascript:void(0);">
                            <xsl:text>ATOM</xsl:text>
                        </a>
                        <p style="font-size: 13px;">
                            <xsl:value-of select="description"/>
                        </p>
                        <div style="font-size: 10px;">
                            Original feeds:
                            <br />
                            <div>
                                <xsl:for-each select="uris">
                                    <a href="{.}">
                                        <xsl:value-of select="."/>
                                    </a>
                                    <br/>
                                </xsl:for-each>
                            </div>
                        </div>
                    </div>
                </div>
            </xsl:for-each>
        </body>
    </xsl:template>

</xsl:stylesheet>
