import os
import sqlite3
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression

SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
DB_PATH = os.path.join(SCRIPT_DIR, "..", "src", "main", "resources", "DBtable", "finance.db")


def load_data():
    conn = sqlite3.connect(DB_PATH)
    query = "SELECT * FROM transactions"
    df = pd.read_sql_query(query, conn)
    conn.close()
    df['properDate'] = pd.to_datetime(df['properDate'])

    return df

def plot_top_spending(df):
    top_spending = df.groupby('category')['paidOut'].sum().sort_values(ascending=False).head(10)
    plt.bar(top_spending.index, top_spending.values)
    plt.title('Top Spending Categories')
    plt.xlabel('Category')
    plt.ylabel('Total Spent')
    plt.xticks(rotation=90, ha="right")
    plt.show()

def get_monthly_spending(df):
    monthly_df = df.groupby(df['properDate'].dt.to_period("M"))['paidOut'].sum()
    return monthly_df

def predict_next_months_spending(monthly_spending):
    monthly_df = monthly_spending.reset_index()
    monthly_df.columns = ['month', 'total_paid_out']
    monthly_df['month_num'] = range(len(monthly_df))

    x = monthly_df[['month_num']]
    y = monthly_df['total_paid_out']
    model = LinearRegression()
    model.fit(x, y)     
    next_month_pred = len(monthly_df)
    amount_pred = model.predict([[next_month_pred]])
    return amount_pred

def  main():
    print("Script started")
    df = load_data()
    plot_top_spending(df)
    monthly = get_monthly_spending(df)
    amount = predict_next_months_spending(monthly)
    print(f"Next month's spending prediction: £{amount[0]:.2f}")



if __name__ == "__main__":

    main()