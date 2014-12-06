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

    <xsl:template match="combinedFeedList">
        <h2>
            <NUMBER>
                <xsl:number value="position()" format="1" />
            </NUMBER>
            <a href="aaa">
                <xsl:value-of select="name"/>
            </a>
        </h2>
        <p>
            <xsl:value-of select="description"/>
        </p>
    </xsl:template>

    <xsl:template match="overViewBean">
        <html>
            <head>
                <style>
                    HEADLINES {
                    font-family:georgia, serif;
                    color:#381704;
                    font-size:18px;
                    letter-spacing:0.1em;
                    line-height:200%;
                    padding-top:11px;
                    }
                    NUMBER {
                    font-family:georgia, serif;

                    font-size:19px;
                    font-weight:bold;
                    line-height:125%;
                    text-align:center;
                    }
                    QUIPPED {
                    font-family:georgia, serif;
                    color:#786E69;
                    font-size:10px;
                    font-weight:bold;
                    letter-spacing:.1em;
                    text-transform:uppercase;
                    padding-bottom:3px;

                    font-family:georgia, serif;
                    color:#786E69;
                    font-size:10px;
                    font-weight:bold;
                    font-style:italic;
                    letter-spacing:.1em;
                    padding-bottom:35px;
                    }
                    PARAGRAPH {
                    font-family:georgia,serif;
                    color:#381704;
                    font-size:12px;
                    font-weight:normal;
                    line-height:150%;
                    padding:0px;
                    }
                    URIS {
                    font-family:georgia,serif;

                    font-size:9px;
                    font-weight:normal;
                    line-height:150%;
                    padding:0px;
                    }
                </style>
                <title>
                    <xsl:value-of select="title"/>
                </title>
            </head>
        </html>
        <body>
            <h1>
                <HEADLINES>
                    <xsl:value-of select="title"/>
                </HEADLINES>
            </h1>
            <xsl:for-each select="combinedFeedList">
                <NUMBER>
                    <xsl:number value="position()" format="1" />
                </NUMBER>
                <xsl:text>   </xsl:text>
                <a onclick='window.open(document.URL+"/html/{name}","_self")' href="javascript:void(0);">
                    <NUMBER><xsl:value-of select="name" /></NUMBER>
                </a>
                <br/>
                <a onclick='window.open(document.URL+"/json/{name}","_self")' href="javascript:void(0);">
                       <xsl:text>JSON</xsl:text>
                </a>
                <xsl:text>, </xsl:text>
                <a onclick='window.open(document.URL+"/atom/{name}","_self")' href="javascript:void(0);">
                       <xsl:text>ATOM</xsl:text>
                </a>
                <p>
                    <PARAGRAPH><xsl:value-of select="description"/></PARAGRAPH>
                </p>
                <QUIPPED>Original feeds:</QUIPPED><br />
                <xsl:for-each select="uris">
                    <a href="{.}">
                    <URIS><xsl:value-of select="."/></URIS>
                    </a>
                    <br />
                </xsl:for-each>
                <br />
            </xsl:for-each>
        </body>
    </xsl:template>

</xsl:stylesheet>
