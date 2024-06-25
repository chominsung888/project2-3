       var xhr = new XMLHttpRequest();
          var url = 'http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json';
          var key = 'a00c456adf2ded3c39df93f44cf40503';

          var today = new Date();
          var yyyy = today.getFullYear().toString();
          var mm = (today.getMonth() + 1).toString().padStart(2, '0');
          var dd = today.getDate().toString().padStart(2, '0');
          var targetDt = yyyy + mm + dd; // 오늘 날짜

          var queryParams = '?' + encodeURIComponent('key') + '=' + encodeURIComponent(key);
          queryParams += '&' + encodeURIComponent('targetDt') + '=' + encodeURIComponent(targetDt);

          xhr.open('GET', url + queryParams);
          xhr.onreadystatechange = function () {
          if (this.readyState == 4 && this.status == 200) {
          var response = JSON.parse(this.responseText);
          var jsonData = JSON.stringify(response);
          console.log(jsonData); // JSON 형식으로 변환된 데이터 확인
          // 이후에는 필요한 처리를 진행할 수 있습니다.
          }
          };

          xhr.send('');