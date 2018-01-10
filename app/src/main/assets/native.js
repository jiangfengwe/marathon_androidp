 var native = {


            //跳转到酒店列表
      toHotel:function() {

               var u = navigator.userAgent;
               var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
               var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端

                 if (isiOS) {//执行iOS native

                  window.webkit.messageHandlers.swift.postMessage({"type":"hotel"});
                   } else if (isAndroid) {
                     //执行安卓native
                     window.android.toHotel();
             }
 
              
          	},

      //跳转到旅游列表
        toTravel: function() {

          var u = navigator.userAgent;
          var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
          var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端

          if (isiOS) {//执行iOS native
        window.webkit.messageHandlers.swift.postMessage({"type":"travel"});
             }else if(isAndroid){
                                   
             //执行安卓native
                  window.android.toTravel();
                                   
           }

 
         }  
 }