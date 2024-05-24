<script setup></script>

<template>
  <form id="createItemFrm" method="post" action="/board/notice/item/create">
    <div class="row">
      <div class="col-2">
        <label for="title">제목</label>
      </div>
      <div class="col-10">
        <input
          type="text"
          id="title"
          name="ancTitle"
          class="form-control"
          v-model="title"
          maxlength="300"
          minlength="1"
        />
      </div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="tags">키워드</label>
      </div>
      <div class="col-10">
        <div id="hashtag" class="form-control">
          <div id="tagItems"></div>
          <div id="tagIpt">
            <input
              type="text"
              id="tags"
              @keydown="fnHashTag($event)"
              placeholder="영문 대소문자, 한글, 숫자, _, -만 사용해주세요."
            />
            <input type="hidden" name="ancKw" />
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-12">
        <textarea
          class="form-control"
          name="ancCotents"
          rows="20"
          maxlength="3000"
          minlength="1"
        ></textarea>
      </div>
    </div>

    <div class="row">
      <div id="btnRow" class="col-12 d-flex justify-content-end">
        <a class="btn btn-sm btn-outline-success" @click="fnSave">
          <font-awesome-icon :icon="['fas', 'floppy-disk']" />
        </a>
        <a class="btn btn-sm btn-outline-danger">
          <font-awesome-icon :icon="['fas', 'eraser']" />
        </a>
        <a class="btn btn-sm btn-outline-secondary" href="/board/notice">
          <font-awesome-icon :icon="['far', 'rectangle-list']" />
        </a>
      </div>
    </div>
  </form>
</template>

<script>
export default {
  data() {
    return {
      title: "",
      keyword: {},
      contents: "",
    };
  },
  methods: {
    fnHashTag(evt) {
      var ipt = document.querySelector("input#tags");
      var value = ipt.value.trim();

      var parent = document.querySelector("div#tagItems");
      var tagcnt = parent.childNodes.length;

      if (evt.keyCode == 13) {
        if (value.length > 10) {
          alert("키워드는 10자 이하로 사용할 수 있습니다.");
          value = value.substring(0, 10);
        }

        var rex = new RegExp(/[^ㄱ-ㅎ가-힣A-Za-z0-9-_]/, "gi");
        value = value.replace(rex, "");

        var idx = Object.keys(this.keyword).length;
        console.log(idx);

        if (idx < 10) {
          var nxIdx = 0;
          if (idx > 0) {
            console.log(Object.keys(this.keyword)[idx - 1]);
            console.log(parseInt(Object.keys(this.keyword)[idx - 1]));
            nxIdx = parseInt(Object.keys(this.keyword)[idx - 1]) + 1;
          }

          this.keyword[nxIdx] = value;
          if (value != "") {
            var badge = document.createElement("badge");
            var delbtn = document.createElement("span");

            badge.className = "badge badge bg-secondary";
            badge.innerText = "#" + value;

            delbtn.className = "deltag";
            delbtn.id = idx;
            delbtn.innerText = "x";

            delbtn.addEventListener("click", this.fnDeltag);

            badge.appendChild(delbtn);
            parent.appendChild(badge);
          }
        } else {
          alert("10개 이상의 키워드를 등록할 수 없습니다.");
          return false;
        }
        ipt.value = "";
      } else if (evt.keyCode == 8) {
        if (value == "" && tagcnt > 0) {
          var target = parent.childNodes[tagcnt - 1].childNodes[1];
          delete this.keyword[target.id];
          target.parentNode.remove();
        }
      }
    },
    fnDeltag(e) {
      delete this.keyword[e.target.id];
      e.target.parentNode.remove();
    },
    fnSave() {
      document.querySelector("form#createItemFrm").submit();
    },
  },
};
</script>

<style>
form#createItemFrm {
  textarea {
    resize: none;
  }
  div.row {
    border-bottom: 1px solid #ececec;
    padding-top: 10px;
    padding-bottom: 10px;

    div label {
      display: flex;
      height: 100%;
      align-items: center;
      justify-content: center;
    }
    div#hashtag {
      display: flex;

      div#tagItems {
        display: flex;
        flex-wrap: no-wrap;
      }
      div#tagIpt {
        width: 100%;
        padding-left: 5px;

        input {
          width: 100%;
          border: none;
          box-shadow: none;
        }
      }

      badge {
        margin-left: 2px;
        margin-right: 2px;
        padding-left: 8px;
        padding-right: 8px;
        line-height: 1.5em;
        font-size: 13px;

        span.deltag {
          padding-left: 3px;
          padding-right: 3px;
          cursor: pointer;
        }
      }
    }

    div#btnRow {
      display: flex;
      justify-content: flex-end;

      a {
        font-size: 20px;
        padding: 5px;
        width: 45px;
        margin: 2px;
      }
    }
  }
}
</style>
