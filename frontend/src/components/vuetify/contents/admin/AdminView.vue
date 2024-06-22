<script setup></script>
<template>
  <v-card id="admin">
    <v-tabs v-model="tab" align-tabs="center" color="deep-purple-accent-4">
      <v-tab value="1">Menus</v-tab>
      <v-tab value="2">Members</v-tab>
    </v-tabs>

    <v-tabs-window v-model="tab">
      <v-tabs-window-item value="1">
        <v-container fluid>
          <v-row>
            <v-col cols="3">
              <v-list class="menu-sample">
                <v-list-item
                  v-for="(item, i) in menus"
                  :key="item"
                  :prepend-icon="item.menuIcon"
                  @click="showDetails(i)"
                  color="primary"
                >
                  <v-icon
                    v-for="char in item.menuName"
                    :key="char"
                    :icon="'fas fa-' + char"
                  ></v-icon>
                </v-list-item>
              </v-list>
            </v-col>
            <v-col cols="9">
              <v-row>
                <v-col cols="12" v-show="this.selected">
                  <v-form @submit.prevent ref="selectedFrm">
                    <v-text-field
                      label="메뉴명"
                      v-model="select.menuName"
                      :rules="menuNameRules"
                    ></v-text-field>
                    <v-text-field
                      label="URL"
                      v-model="select.menuLink"
                    ></v-text-field>
                    <v-text-field
                      label="ICON"
                      v-model="select.menuIcon"
                    ></v-text-field>
                    <v-select
                      label="권한"
                      v-model="select.menuLevel"
                      :items="[
                        { title: '일반 유저', value: 1 },
                        { title: '관리자', value: 2 },
                      ]"
                    ></v-select>
                    <v-select
                      label="공개여부"
                      v-model="select.menuOpenYn"
                      :items="[
                        { title: '공개', value: 'Y' },
                        { title: '비공개', value: 'N' },
                      ]"
                    ></v-select>
                    <v-text-field
                      label="순서"
                      v-model="select.sortOrder"
                    ></v-text-field>
                  </v-form>
                </v-col>

                <v-col cols="12" v-show="this.newMenu">
                  <v-form @submit.prevent ref="newMenuFrm">
                    <v-row>
                      <v-col cols="4">
                        <v-list class="menu-sample">
                          <v-list-item :prepend-icon="newValue.menuIcon">
                            <v-icon
                              v-for="char in newValue.menuName"
                              :key="char"
                              :icon="'fas fa-' + char"
                            ></v-icon>
                          </v-list-item>
                        </v-list>
                      </v-col>
                    </v-row>
                    <v-text-field
                      label="메뉴명"
                      v-model="newValue.menuName"
                      :rules="menuNameRules"
                    ></v-text-field>
                    <v-text-field
                      label="URL"
                      v-model="newValue.menuLink"
                      placeholder="ex> /notice/board"
                    ></v-text-field>
                    <v-text-field
                      label="ICON"
                      v-model="newValue.menuIcon"
                      placeholder="font-awesome free tier icon만 사용해주세요. ex> fas fa-user"
                    ></v-text-field>
                    <v-select
                      label="권한"
                      v-model="newValue.menuLevel"
                      :items="[
                        { title: '일반 유저', value: 1 },
                        { title: '관리자', value: 2 },
                      ]"
                    ></v-select>
                    <v-select
                      label="공개여부"
                      v-model="newValue.menuOpenYn"
                      :items="[
                        { title: '공개', value: 'Y' },
                        { title: '비공개', value: 'N' },
                      ]"
                    ></v-select>
                    <v-text-field
                      label="순서"
                      v-model="newValue.sortOrder"
                      placeholder="순서를 입력하지 않으면, 가장 마지막 순서로 등록됩니다."
                    ></v-text-field>
                  </v-form>
                </v-col>
              </v-row>
              <v-btn class="btns" @click="validate">저장</v-btn>
              <v-btn class="btns" @click="fnNewMenu">신규</v-btn>
              <v-btn class="btns" @click="fnInit">초기화</v-btn>
            </v-col>
          </v-row>
        </v-container>
      </v-tabs-window-item>

      <v-tabs-window-item value="2">
        <v-container fluid>
          <v-row>
            <v-col cols="12" md="4"> adfasdfasdfasdf </v-col>
          </v-row>
        </v-container>
      </v-tabs-window-item>
    </v-tabs-window>
  </v-card>
</template>

<script>
export default {
  data: () => ({
    menus: [
      {
        menuName: "notice",
        menuLink: "/board/notice",
        menuIcon: "bullhorn",
        menuLevel: "1",
        menuOpenYn: "Y",
        sortOrder: 1,
      },
    ],
    tab: null,
    select: {
      menuName: null,
      menuLink: null,
      menuIcon: null,
      menuLevel: null,
      menuOpenYn: null,
      sortOrder: null,
    },
    newValue: {
      menuName: null,
      menuLink: null,
      menuIcon: null,
      menuLevel: null,
      menuOpenYn: null,
      sortOrder: null,
    },
    selected: false,
    newMenu: true,
  }),
  computed: {
    menuNameRules() {
      const rules = [];

      const nullChk = (v) => {
        if (v) return true;
        else return "메뉴명은 필수입니다.";
      };

      const regChk = (v) => {
        var regExp = /[a-z0-9]/;
        var chk = regExp.test(v.trim());

        if (chk) return true;
        else return "메뉴명은 영어와 숫자만 이용해주세요.";
      };

      const lengthChk = (v) => {
        if (v.length <= 50) return true;
        else return "메뉴명은 50자를 넘을 수 없습니다.";
      };

      rules.push(nullChk);
      rules.push(regChk);
      rules.push(lengthChk);

      return rules;
    },

    menuURLRules() {
      const rules = [];

      const nullChk = (v) => {
        if (v) return true;
        else return "URL은 필수입니다.";
      };

      const lengthChk = (v) => {
        if (v.length <= 100) return true;
        else return "URL은 100자를 넘을 수 없습니다.";
      };

      rules.push(nullChk);
      rules.push(lengthChk);

      return rules;
    },

    menuIconRules() {
      const rules = [];

      const nullChk = (v) => {
        if (v) return true;
        else return "ICON은 필수입니다.";
      };

      const lengthChk = (v) => {
        if (v.length <= 50) return true;
        else return "ICON은 50자를 넘을 수 없습니다.";
      };

      rules.push(nullChk);
      rules.push(lengthChk);

      return rules;
    },
  },
  methods: {
    showDetails(idx) {
      this.selected = true;
      this.newMenu = false;
      this.select.menuName = this.menus[idx].menuName;
      this.select.menuLink = this.menus[idx].menuLink;
      this.select.menuIcon = this.menus[idx].menuIcon;
      this.select.menuLevel = this.menus[idx].menuLevel;
      this.select.menuOpenYn = this.menus[idx].menuOpenYn;
      this.select.sortOrder = this.menus[idx].sortOrder;
    },
    fnNewMenu() {
      this.selected = false;
      this.newMenu = true;
      this.select.menuName = this.newValue.menuName;
      this.select.menuLink = this.newValue.menuLink;
      this.select.menuIcon = this.newValue.menuIcon;
      this.select.menuLevel = this.newValue.menuLevel;
      this.select.menuOpenYn = this.newValue.menuOpenYn;
      this.select.sortOrder = this.newValue.sortOrder;
    },
    fnInit() {
      this.select.menuName = null;
      this.select.menuLink = null;
      this.select.menuIcon = null;
      this.select.menuLevel = null;
      this.select.menuOpenYn = null;
      this.select.sortOrder = null;

      this.newValue.menuName = null;
      this.newValue.menuLink = null;
      this.newValue.menuIcon = null;
      this.newValue.menuLevel = null;
      this.newValue.menuOpenYn = null;
      this.newValue.sortOrder = null;
    },
    async validate() {
      var form = this.selected ? this.$refs.selectedFrm : this.$refs.newMenuFrm;
      let chk = await form.validate();

      if (!chk.valid) {
        alert("입력 값을 다시 확인해주세요.");
        return false;
      } else {
        this.fnSave();
      }
    },
    async fnSave() {
      let valueObj = this.selected ? this.select : this.newValue;
      let url = this.selected
        ? "/rest/admin/menu/update"
        : "/rest/admin/menu/create";
      await this.axios.post(url, JSON.stringify(valueObj)).then((res) => {
        console.log(res.data);
      });
    },
  },
};
</script>
<style scoped>
#admin {
  margin: 10px;
  padding: 30px;
}

.menu-sample {
  font-size: 10px;
}

.btns {
  margin: 5px;
}
</style>
