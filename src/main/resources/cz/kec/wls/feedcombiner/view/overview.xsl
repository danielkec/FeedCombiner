<?xml version="1.0" encoding="UTF-8"?>
<!--
    Document   : overview.xsl
    Created on : 5. prosinec 2014, 22:30
    Author     : Daniel Kec
    Description:
        Creates html which is served as combined feed overview.
        Used as UI for all the CRUD ops.
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
                <link rel="stylesheet" type="text/css" href="../css/main.css" media="screen" />
                <script type='text/javascript' src='../js/lib/jquery-2.1.1.min.js'></script>
                <script type='text/javascript' src='../js/lib/knockout-3.2.0.js'></script>
                <script type='text/javascript' src='../js/Main.js' defer="defer"></script>
                <script type='text/javascript' src='../js/KOModel.js' defer="defer"></script>
                <title>
                    <xsl:value-of select="title"/>
                </title>
            </head>
        </html>
        <body>
            <h1>
                <headlines>
                    <xsl:value-of select="title"/>
                    <div class="countdown-text"><xsl:text>Remaining time to next feed refresh:</xsl:text></div><div id="countdown">?</div>
                    <p>
                        <button data-bind='click: changeTimerInterval'>Change refresh interval</button>
                        <button data-bind='click: createFeed'>Create cobined feed</button>
                    </p>
                </headlines>
            </h1>
            <xsl:for-each select="combinedFeedList">
                <div class="container" id="container{position()}">
                    <div style="float: right;
                            width: 100px;">
                        <p>
                            <button data-bind='click: deleteFeed.bind($data,"{name}")'>Delete</button>
                        </p>
                        <p>
                            <button data-bind='click: updateFeed.bind($data,"container{position()}")'>Update</button>
                        </p>
                    </div>
                    <div style="overflow: hidden;">
                        <xsl:number value="position()" format="1" />
                        <xsl:text> </xsl:text>
                        <b id="name">
                            <xsl:value-of select="name"/>
                        </b>
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
                        <p>
                            <input type="text" id="description" style="font-size: 13px;" value="{description}"/>
                        </p>
                        <div style="font-size: 10px;">
                            Original feeds: <button class="small-btn" data-bind='click: addUrl.bind($data,"{name}","{position()}")'>Add</button>
                            <br />
                            <div id="urls{position()}">
                                <xsl:for-each select="uris">
                                    <div id="url.{count(../preceding-sibling::combinedFeedList) + 1}{position()}">
                                    <button class="small-btn" data-bind='click: deleteUrl.bind($data,"{../name}",{count(../preceding-sibling::combinedFeedList) + 1}{position()})'>x</button>
                                    <xsl:text> </xsl:text>
                                    <a class="url" href="{.}">
                                        <xsl:value-of select="."/>
                                    </a>
                                    </div>
                                </xsl:for-each>
                            </div>
                        </div>
                    </div>
                </div>
            </xsl:for-each>
        </body>
    </xsl:template>

</xsl:stylesheet>
