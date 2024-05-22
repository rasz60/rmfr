<script setup></script>

<template>
  <div class="boardBody">
    <div class="btn btn-lg btn-dark disabled">
      <span class="col-1">순번</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn btn-lg btn-outline-light text-dark">
      <span class="col-1">10</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn btn-lg btn-outline-light text-dark">
      <span class="col-1">9</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn btn-lg btn-outline-light text-dark">
      <span class="col-1">8</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn btn-lg btn-outline-light text-dark">
      <span class="col-1">7</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn btn-lg btn-outline-light text-dark">
      <span class="col-1">6</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn btn-lg btn-outline-light text-dark">
      <span class="col-1">5</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn btn-lg btn-outline-light text-dark">
      <span class="col-1">4</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn btn-lg btn-outline-light text-dark">
      <span class="col-1">3</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn btn-lg btn-outline-light text-dark">
      <span class="col-1">2</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn btn-lg btn-outline-light text-dark">
      <span class="col-1">1</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
  </div>
  <nav aria-label="Page navigation example">
    <ul class="pagination">
      <li class="page-item">
        <a class="page-link text-dark" href="#" aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <li class="page-item">
        <a class="page-link text-dark" href="#">1</a>
      </li>
      <li class="page-item">
        <a class="page-link text-dark" href="#" aria-label="Next">
          <span aria-hidden="true">&raquo;</span>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {},
};
</script>

<style>
div#content {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
</style>

<style scoped>
div.boardBody {
  margin-top: 40px;
  width: 95%;

  div.btn {
    width: 100%;
    border-radius: 10px !important;
    height: 50px;
    margin: 5px;
    display: flex;
    align-items: center;

    span {
      font-size: 13px;
    }
  }
  div.btn.disabled {
    font-weight: 500;
    span {
      font-size: 15px;
    }
  }

  div.btn.btn-outline-light {
    border-color: #ececec;
  }
}
</style>
