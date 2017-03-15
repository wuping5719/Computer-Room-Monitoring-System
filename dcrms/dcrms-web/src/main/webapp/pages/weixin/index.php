<?php
   // 判断用户发送消息类型
   define("TOKEN", "weixin");

   $wechatObj = new wechatCallbackapiTest();
   if (!isset($_GET['echostr'])) {
      $wechatObj->responseMsg();
   } else {
      $wechatObj->valid();
   }

   class wechatCallbackapiTest {
      public $fromUsername='';
      public $toUsername='';
      public function valid() {
          $echoStr = $_GET["echostr"];
          if($this->checkSignature()) {
              echo $echoStr;
              exit;
          }
      }

      private function checkSignature() {
         $signature = $_GET["signature"];
         $timestamp = $_GET["timestamp"];
         $nonce = $_GET["nonce"];
         $token = TOKEN;
         $tmpArr = array($token, $timestamp, $nonce);
         sort($tmpArr, SORT_STRING);
         $tmpStr = implode($tmpArr);
         $tmpStr = sha1($tmpStr);

         if($tmpStr == $signature) {
            return true;
         } else {
            return false;
         }
      }

      public function responseMsg() {
         $postStr = $GLOBALS["HTTP_RAW_POST_DATA"];
         $postObj = simplexml_load_string($postStr, 'SimpleXMLElement', LIBXML_NOCDATA);
         $fromUsername = $postObj->FromUserName;
         $toUsername = $postObj->ToUserName;
         $type = $postObj->MsgType;
         $event = $postObj->Event;
         $event_Key = $postObj->EventKey;
         $mid = $postObj->MediaId;
         $link = $postObj->Url;

         $latitude = $postObj->Location_X;
         $longitude = $postObj->Location_Y;
         $keyword = trim($postObj->Content);
         $time = time();
         $textTpl = "<xml>
             <ToUserName><![CDATA[%s]]></ToUserName>
             <FromUserName><![CDATA[%s]]></FromUserName>
             <CreateTime>%s</CreateTime>
             <MsgType><![CDATA[text]]></MsgType>
             <Content><![CDATA[%s]]></Content>
           </xml>";
         if ($keyword!='') {
            $contentStr=$keyword;
         }
         elseif ($type=="image") {
            $contentStr="您发送的是图片消息,消息的MediaId是".$mid;
         }
         elseif ($type=="voice") {
            $contentStr="您发送的是语音消息,消息的MediaId是".$mid;
         }
         elseif ($type=="video") {
            $contentStr="您发送的是视频消息,消息的MediaId是".$mid;
         }
         elseif ($type=="location") {
            $contentStr="您发送的是地理位置消息,您的地理位置是：经度".$latitude."维度：".$longitude;
         }
         elseif ($type=="link") {
            $contentStr="您发送的是链接消息,消息链接为".$link;
         }
         elseif ($type=="event" && $event=="subscribe") {
            $contentStr="欢迎关注机房监控云系统的微信公众账号！";
         }
         elseif ($type="event" && $event=="unsubscribe") {
            $contentStr="感谢您的关注！";
         }
         else {
            echo "";
         }

         $resultStr = sprintf($textTpl, $fromUsername, $toUsername, $time, $contentStr);
         echo $resultStr;
     }

  }
?>
