<?xml version="1.0" encoding="UTF-8"?>
<!--
    Document   : atomTohtml.xsl
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

    <xsl:template match="atom:entry">
        <div id="container">
        <h3>
            <a href="{atom:link/@href}">
                <xsl:value-of select="atom:title"/>
            </a>
        </h3>
        <p>
            <xsl:value-of select="atom:summary" disable-output-escaping="yes"/>
        </p>
        </div>
    </xsl:template>

    <xsl:template match="/atom:feed">
        <html>
            <head>
                <style>
                    body {
                        text-align: center;
                        font-family:georgia, serif;
                    }

                    #container {
                        margin: 0 auto;
                        width: 800px;
                        text-align: left;
                    }
                </style>
                <title>
                    <xsl:value-of select="atom:title"/>
                </title>
            </head>
        </html>
        <body>
            <h1>
                <a href="{atom:link/@href}">
                    <xsl:value-of select="atom:title"/>
                </a>
            </h1>
            <xsl:apply-templates/>
        </body>
    </xsl:template>

</xsl:stylesheet>
