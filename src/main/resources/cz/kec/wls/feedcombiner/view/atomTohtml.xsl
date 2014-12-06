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

        <h3>
            <a href="{atom:link/@href}">
                <xsl:value-of select="atom:title"/>
            </a>
        </h3>
        <p>
            <xsl:value-of select="atom:summary" disable-output-escaping="yes"/>
        </p>

    </xsl:template>

    <xsl:template match="/atom:feed">
        <html>
            <head>
                <style><![CDATA[
                    body {
                    text-align: center;
                    font-family:georgia, verdana, serif;
                    background-color: #E0E0E0 ;
                    }

                    #container {
                    margin: 0 auto;
                    width: 800px;
                    text-align: left;
                    background-color: white;
                    padding: 20px;
                    }
                ]]></style>
                <script>
                    function getfixedUrl(protocol){
                        return document.URL.replace("/html/","/"+protocol+"/");
                    }
                    function redirect(protocol){
                        var fixedUrl = getfixedUrl(protocol);
                        window.open(fixedUrl,"_self");
                    }
                </script>
                <title>
                    <xsl:value-of select="atom:title"/>
                </title>
            </head>
        </html>
        <body>
            <h1>
                <xsl:value-of select="atom:title"/>
            </h1>
            <h4>
                <xsl:value-of select="atom:subtitle"/>
            </h4>
            <div style="margin: 0 auto;
                        width: 800px;
                        font-size:11px;
                        padding-right:30;
                        text-align: left;">
                        <a onclick="redirect('json');" href="javascript:void(0);">
                            <xsl:text>JSON</xsl:text>
                        </a>
                        <xsl:text>,</xsl:text>
                        <a onclick="redirect('atom');" href="javascript:void(0);">
                            <xsl:text>ATOM</xsl:text>
                        </a>
            </div>
            <div id="container">
                <xsl:apply-templates/>
            </div>
        </body>
    </xsl:template>

</xsl:stylesheet>
