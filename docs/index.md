<!-- ---
hide:
  - navigation
--- -->

# PoC Load Testing

[file-issues]: https://github.com/sebastian-sommerfeld-io/poc-load-testing/issues

This project is a PoC for Load Testing with [Gatling](https://gatling.io).

## Usage

Gatling provides a cloud-hosted web application <https://computer-database.gatling.io> for running sample simulations.

Gatling test simulations are defined in the `src` directory. To maintain a simple setup, the [JavaScript version of Gatling](https://docs.gatling.io/tutorials/scripting-intro-js) is used.

To run the tests, run `docker compose up` or `npx gatling run --simulation <the_simulation>` in the root directory of this project.

## Contact

Feel free to contact me via <sebastian@sommerfeld.io> or [raise an issue in this repository][file-issues].
