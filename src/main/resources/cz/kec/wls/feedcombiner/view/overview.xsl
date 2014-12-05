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

  <xsl:template match="atom:entry">
    <h2>
      <a href="{atom:link/@href}">
        <xsl:value-of select="atom:title"/>
      </a>
    </h2>
    <p>
      <xsl:value-of select="atom:summary"/>
    </p>
  </xsl:template>

  <xsl:template match="/atom:feed">
    <html>
      <head>
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
